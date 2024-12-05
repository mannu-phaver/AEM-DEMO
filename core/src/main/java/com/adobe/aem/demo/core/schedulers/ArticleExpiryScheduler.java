package com.adobe.aem.demo.core.schedulers;

// public class ArticleExpiryScheduler {
// }
// package com.example.schedulers;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Component(service = Runnable.class, immediate = true)
@Designate(ocd = ArticleExpirySchedulerConfig.class)
public class ArticleExpiryScheduler implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ArticleExpiryScheduler.class);

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    private Replicator replicator;

    @Reference
    private Scheduler scheduler;

    private volatile ArticleExpirySchedulerConfig config;

    @Activate
    @Modified
    protected void activate(ArticleExpirySchedulerConfig config) {
        this.config = config;
        scheduleJob();
    }

    private void scheduleJob() {
        if (config.enable()) {
            ScheduleOptions options = scheduler.EXPR(config.schedulerExpression());
            options.name(config.schedulerName());
            options.canRunConcurrently(false);
            scheduler.schedule(this, options);
        } else {
            scheduler.unschedule(config.schedulerName());
        }
    }

    @Override
    public void run() {
        log.info("Inside the run method of ArticleExpiryScheduler...");
        Map<String, Object> authInfo = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "subserviceName");
        try (ResourceResolver resolver = resolverFactory.getServiceResourceResolver(authInfo)) {
            PageManager pageManager = resolver.adaptTo(PageManager.class);
            if (pageManager != null) {
                Page rootPage = pageManager.getPage(config.path());
                if (rootPage != null) {
                    Iterator<Page> childPages = rootPage.listChildren();
                    Date today = new Date();
                    while (childPages.hasNext()) {
                        Page page = childPages.next();
                        Resource contentResource = page.getContentResource();
                        if (contentResource != null) {
                            ValueMap properties = contentResource.getValueMap();
                            Date articleExpiry = properties.get("articleExpiry", Date.class);
                            if (articleExpiry != null && articleExpiry.compareTo(today) <= 0) {
                                log.info("Expired page: {}", page.getPath());
                                contentResource.adaptTo(ModifiableValueMap.class).put("status", "expired");
                                resolver.commit();
                            }
                        }
                    }
                } else {
                    log.warn("Root page not found: {}", config.path());
                }
            }
        } catch (Exception e) {
            log.error("Error during execution of ArticleExpiryScheduler", e);
        }
    }

    // @Override
    // public void run() {
    //     log.info("Inside the run method of ArticleExpiryScheduler...");
    //     try (ResourceResolver resolver = resolverFactory.getServiceResourceResolver(null)) {
    //         PageManager pageManager = resolver.adaptTo(PageManager.class);
    //         if (pageManager != null) {
    //             Page rootPage = pageManager.getPage(config.path());
    //             if (rootPage != null) {
    //                 Iterator<Page> childPages = rootPage.listChildren();
    //                 Date today = new Date();
    //                 while (childPages.hasNext()) {
    //                     Page page = childPages.next();
    //                     Resource contentResource = page.getContentResource();
    //                     if (contentResource != null) {
    //                         ValueMap properties = contentResource.getValueMap();
    //                         Date articleExpiry = properties.get("articleExpiry", Date.class);
    //                         if (articleExpiry != null && articleExpiry.compareTo(today) <= 0) {
    //                             replicator.replicate(resolver.adaptTo(Session.class),
    //                                     ReplicationActionType.ACTIVATE, page.getPath());
    //                             log.info("Published page: {}", page.getPath());
    //                         }
    //                     }
    //                 }
    //             } else {
    //                 log.warn("Root page not found: {}", config.path());
    //             }
    //         }
    //     } catch (Exception e) {
    //         log.error("Error during execution of ArticleExpiryScheduler", e);
    //     }
    // }
}
