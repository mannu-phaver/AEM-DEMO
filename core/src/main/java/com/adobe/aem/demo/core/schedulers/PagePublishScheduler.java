package com.adobe.aem.demo.core.schedulers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;

@Component(service = Runnable.class, immediate = true)
public class PagePublishScheduler implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(PagePublishScheduler.class);

    @Reference
    private Scheduler scheduler;

    @Reference
    private Replicator replicator;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    private String cronExpression = "0/2 * * * * ?";
    private String pagePath = "/content";

    @Activate
    protected void activate() {
        ScheduleOptions options = scheduler.EXPR(cronExpression);
        options.name("PagePublishScheduler");
        options.canRunConcurrently(false);

        scheduler.schedule(this, options);
        LOG.info("Scheduler activated with cron expression: {}", cronExpression);
    }

    @Override
    public void run() {
        LOG.info("Scheduler triggered. Attempting to publish page: {}", pagePath);
        try (ResourceResolver resourceResolver = getServiceResourceResolver()) {
            if (resourceResolver == null) {
                LOG.error("Unable to obtain a resource resolver. Aborting replication.");
                return;
            }

            Session session = resourceResolver.adaptTo(Session.class);
            if (session == null) {
                LOG.error("Session could not be obtained. Replication aborted.");
                return;
            }

            Resource rootResource = resourceResolver.getResource(pagePath);
            if (rootResource == null) {
                LOG.error("Page path {} does not exist. Aborting replication.", pagePath);
                return;
            }

            publishChildPages(rootResource, session);
            LOG.info("All child pages under {} have been successfully published.", pagePath);
        } catch (Exception e) {
            LOG.error("Error during publishing the page: {}", pagePath, e);
        }
    }

    private void publishChildPages(Resource resource, Session session) {
        try {
            LOG.info("Publishing page: {}", resource.getPath());
            replicator.replicate(session, ReplicationActionType.ACTIVATE, resource.getPath());

            Iterator<Resource> children = resource.listChildren();
            while (children.hasNext()) {
                Resource child = children.next();
                publishChildPages(child, session); // Recursively publish child pages
            }
        } catch (ReplicationException e) {
            LOG.error("Error publishing page: {}", resource.getPath(), e);
        }
    }

    private ResourceResolver getServiceResourceResolver() throws LoginException {
        Map<String, Object> authInfo = new HashMap<>();
        authInfo.put(ResourceResolverFactory.SUBSERVICE, "manoj");

        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(authInfo);
        } catch (LoginException e) {
            LOG.error("Failed to obtain service resource resolver. Check the service user mapping and permissions.", e);
            throw e;
        }
        return resourceResolver;
    }
}
