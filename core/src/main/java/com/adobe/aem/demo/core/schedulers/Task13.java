package com.adobe.aem.demo.core.schedulers;

// public class Task13 {
// }
// package com.Project.core.schedulers;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventConstants;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;

@Component(service = Runnable.class, immediate = true, property = {
    EventConstants.EVENT_FILTER + "=(path=/content/*)"
})
@Designate(ocd = Task13.Config.class)
public class Task13 implements Runnable {

    @ObjectClassDefinition(name = "Task 13 scheduler")
    public @interface Config {

        @AttributeDefinition(name = "Cron Expression", description = "Enter the cron expression")
        String scheduler_cron_expression() default "0 0/2 * 1/1 * ? *";

    }

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    private Scheduler scheduler;

    @Reference
    private Replicator replicator;

    private String cronExpression;

    private static final Logger LOG = LoggerFactory.getLogger(Task13.class);

    @Activate
    protected void activate(Config config) {
        this.cronExpression = config.scheduler_cron_expression();
        ScheduleOptions options = scheduler.EXPR(cronExpression);
        options.name("Task 13 Scheduler");
        options.canRunConcurrently(false);

        scheduler.schedule(this, options);
        LOG.info("Scheduler activated with cron expression: {}", cronExpression);
    }

    @Override
    public void run() {
        LOG.info("Task13 Scheduler started.");

        try (ResourceResolver resolver = resolverFactory.getServiceResourceResolver(Collections.singletonMap(
                ResourceResolverFactory.SUBSERVICE, "manoj"))) {

            Session session = resolver.adaptTo(Session.class);
            QueryManager queryManager = session.getWorkspace().getQueryManager();

            // Constrained Query
            String sqlQuery = "SELECT * FROM [cq:PageContent] WHERE [ExpiryDate] IS NOT NULL AND ISDESCENDANTNODE('/content')";
            Query query = queryManager.createQuery(sqlQuery, Query.JCR_SQL2);
            Iterator<Node> results = query.execute().getNodes();

            // Log the number of results
            int count = 0;
            while (results.hasNext()) {
                Node pageContent = results.next();
                count++;
                String pagePath = pageContent.getParent().getPath();
                Calendar expiryDate = pageContent.getProperty("ExpiryDate").getDate();

                if (expiryDate.before(Calendar.getInstance())) {
                    replicator.replicate(session, ReplicationActionType.DEACTIVATE, pagePath);
                    LOG.info("Unpublished page: {}", pagePath);
                } else if (expiryDate.compareTo(Calendar.getInstance()) == 0) {
                    replicator.replicate(session, ReplicationActionType.ACTIVATE, pagePath);
                    LOG.info("Published page: {}", pagePath);
                }
            }

            LOG.info("Query returned {} nodes", count);

        } catch (ReplicationException e) {
            LOG.error("Replication error occurred", e);
        } catch (Exception e) {
            LOG.error("Error in Task13 Scheduler", e);
        }
    }

}
