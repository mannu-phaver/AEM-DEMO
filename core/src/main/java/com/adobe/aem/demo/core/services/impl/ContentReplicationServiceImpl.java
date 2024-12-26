package com.adobe.aem.demo.core.services.impl;

// public class ContentReplicationServiceImpl {

// }
// package com.adobe.aem.demo.core.services.impl;

import com.adobe.aem.demo.core.services.ContentReplicationService;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Node;
import javax.jcr.Session;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component(service = ContentReplicationService.class)
public class ContentReplicationServiceImpl implements ContentReplicationService {

    @Reference
    Replicator replicator;

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    public void replicateContent(String payloadPath) {
        System.out.println("Starting replication for path: " + payloadPath);
        Map<String, Object> authInfo = Collections.singletonMap(
                ResourceResolverFactory.SUBSERVICE, "mannu");

        try (ResourceResolver resolver = resolverFactory.getServiceResourceResolver(authInfo)) {
            if (resolver == null) {
                System.out.println("Failed to get ResourceResolver.");
                return;
            }
            Session session = resolver.adaptTo(Session.class);
            replicate(session, payloadPath);

            Set<String> assetPaths = getAssetsOnPage(resolver, payloadPath);
            for (String assetPath : assetPaths) {
                replicate(session, assetPath);
            }
            System.out.println("Replication completed for path: " + payloadPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Set<String> getAssetsOnPage(ResourceResolver resolver, String payloadPath) {
        PageManager pageManager = resolver.adaptTo(PageManager.class);
        Page page = Objects.requireNonNull(pageManager).getPage(payloadPath);
        if (page == null) {
            return new LinkedHashSet<>();
        }
        Resource resource = page.getContentResource();
        Node node = resource.adaptTo(Node.class);

        // Search for asset references
        Map<String, ?> assetMap = new com.day.cq.dam.commons.util.AssetReferenceSearch(node,
                "/content/dam", resolver).search();

        return assetMap.keySet();
    }

    private void replicate(Session session, String path) {
        try {
            System.out.println("Replicating: " + path);
            replicator.replicate(session, ReplicationActionType.ACTIVATE, path);
        } catch (ReplicationException e) {
            System.out.println("Replication failed for: " + path + " | Error: " + e.getMessage());
        }
    }
}

