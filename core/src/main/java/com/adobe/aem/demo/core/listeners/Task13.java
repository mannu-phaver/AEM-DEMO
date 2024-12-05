package com.adobe.aem.demo.core.listeners;

// public class Task13 {
// }
// package com.Project.core.listeners;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;

@Component(service = EventHandler.class, immediate = true, property = {
    EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC
})
public class Task13 implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(Task13.class);

    @Override
    public void handleEvent(Event event) {
        try {
            String action = (String) event.getProperty("replicationType");
            String pagePath = (String) event.getProperty("path");
            ReplicationAction replicationAction = ReplicationAction.fromEvent(event);
            LOG.info("hii");
            if (replicationAction != null && ReplicationActionType.ACTIVATE.equals(replicationAction.getType())) {

                String publishedPagePath = replicationAction.getPath();
                LOG.info("Page published: {}", publishedPagePath);
            } else if (ReplicationActionType.DEACTIVATE.getName().equals(action)) {
                LOG.info("Page unpublished: {}", pagePath);
            }
        } catch (Exception e) {
            LOG.error("Error in handling replication event", e);
        }
    }
}
