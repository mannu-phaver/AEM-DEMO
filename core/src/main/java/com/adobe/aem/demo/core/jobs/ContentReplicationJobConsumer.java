package com.adobe.aem.demo.core.jobs;

// public class ContentReplicationJobConsumer {

// }
// package com.adobe.aem.demo.core.jobs;

import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.adobe.aem.demo.core.services.ContentReplicationService;

import static org.apache.sling.event.jobs.consumer.JobConsumer.PROPERTY_TOPICS;

@Component(
        immediate = true,
        service = JobConsumer.class,
        property = {
                PROPERTY_TOPICS + "=" + "content/replication/job"
        }
)
public class ContentReplicationJobConsumer implements JobConsumer {

    @Reference
    ContentReplicationService replicationService;

    @Override
    public JobResult process(Job job) {
        System.out.println("ContentReplicationJobConsumer started for topic: " + job.getTopic());
        try {
            String payloadPath = job.getProperty("payload").toString();
            System.out.println("Processing replication for path: " + payloadPath);

            replicationService.replicateContent(payloadPath);
            System.out.println("Replication completed successfully for: " + payloadPath);

            return JobResult.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return JobResult.FAILED;
        }
    }
}

