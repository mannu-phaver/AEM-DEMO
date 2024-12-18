package com.adobe.aem.demo.core.services.impl;

// public class hi3 {

// }
// import com.day.cq.dam.api.Asset;
// import com.day.cq.dam.commons.util.AssetReferenceSearch;
// import com.day.cq.replication.ReplicationActionType;
// import com.day.cq.replication.ReplicationException;
// import com.day.cq.replication.Replicator;
// import com.day.cq.wcm.api.Page;
// import com.day.cq.wcm.api.PageManager;
// import org.apache.sling.api.resource.Resource;
// import org.apache.sling.api.resource.ResourceResolver;
// import org.osgi.service.component.annotations.Component;
// import org.osgi.service.component.annotations.Reference;
// import com.adobe.aem.demo.core.services.ReplicationService;
// import com.adobe.aem.demo.core.services.ResourceResolverService;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import javax.jcr.Node;
// import javax.jcr.Session;
// import java.util.LinkedHashSet;
// import java.util.Map;
// import java.util.Objects;
// import java.util.Set;

// import static com.day.cq.dam.api.DamConstants.MOUNTPOINT_ASSETS;
import static org.osgi.service.event.EventConstants.SERVICE_ID;
// import static com.adobe.aem.demo.core.constants.AppConstants.EQUALS;
import static com.adobe.aem.demo.core.services.impl.ReplicationServiceImpl.SERVICE_NAME;
// package com.adobe.aem.demo.core.services.impl;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.commons.util.AssetReferenceSearch;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
// import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import javax.jcr.Node;
// import javax.jcr.RepositoryException;
import javax.jcr.Session;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.day.cq.dam.api.DamConstants.MOUNTPOINT_ASSETS;


import com.adobe.aem.demo.core.services.ReplicationService;
@Component(
        service = ReplicationService.class,
        property = {
                SERVICE_ID + "=" + SERVICE_NAME
        }
)
public class ReplicationServiceImpl implements ReplicationService {

    protected static final String SERVICE_NAME = "mannu";

    // private static final String TAG = ReplicationServiceImpl.class.getSimpleName();
    // private static final Logger LOGGER = LoggerFactory.getLogger(ReplicationServiceImpl.class);

    @Reference
    Replicator replicator;

  
    @Reference
    private ResourceResolverFactory factory;

    @Override
    public void replicateContent(String payload) {
        // LOGGER.info("{}: trying to replicate: {}", TAG, payload);
        // Getting resource resolver
       final Map<String, Object> authInfo = Collections.singletonMap(
                ResourceResolverFactory.SUBSERVICE,
                "mannu");
       ResourceResolver resourceResolver;
    try {
        resourceResolver = factory.getServiceResourceResolver(authInfo);
   
        // Getting the session
        Session session = resourceResolver.adaptTo(Session.class);
        // Replicate the page
        replicate(session, payload);
        // Get all the assets on the page(s)
        Set<String> assetsOnPage = getAssetsOnPage(resourceResolver, payload);
        for (String assetPath : assetsOnPage) {
            replicate(session, assetPath);
        }
        // LOGGER.info("{}: replication completed successfully", TAG);
    
} catch (LoginException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
    }
    private Set<String> getAssetsOnPage(ResourceResolver resourceResolver, String payload) {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        Page page = Objects.requireNonNull(pageManager).getPage(payload);
        if (page == null) {
            return new LinkedHashSet<>();
        }
        Resource resource = page.getContentResource();
        AssetReferenceSearch assetReferenceSearch = new AssetReferenceSearch(resource.adaptTo(Node.class),
                MOUNTPOINT_ASSETS, resourceResolver);
        Map<String, Asset> assetMap = assetReferenceSearch.search();
        return assetMap.keySet();
    }

    private void replicate(Session session, String path) {
        try {
            // LOGGER.info("{}: Replicating: {}", TAG, path);
            replicator.replicate(session, ReplicationActionType.ACTIVATE, path);
        } catch (ReplicationException e) {
            // LOGGER.error("{}: replication failed due to: {}", TAG, e.getMessage());
        }
    }
}