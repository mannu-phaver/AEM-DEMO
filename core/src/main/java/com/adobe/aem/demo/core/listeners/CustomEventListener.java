package com.adobe.aem.demo.core.listeners;

// public class CustomEventListener {

// }
// package com.adobe.aem.demo.core.listeners;

import com.day.cq.wcm.api.PageEvent;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(
        immediate = true,
        service = EventHandler.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "= Custom Event Listener for page updates",
                EventConstants.EVENT_TOPIC + "=" + PageEvent.EVENT_TOPIC
        }
)
public class CustomEventListener implements EventHandler {

    @Reference
    JobManager jobManager;

    @Override
    @SuppressWarnings("unchecked")
    public void handleEvent(Event event) {
        System.out.println("CustomEventListener triggered: " + event.getTopic());
        try {
            List<HashMap<String, Object>> modifications = (List<HashMap<String, Object>>) event.getProperty("modifications");
            if (modifications != null && !modifications.isEmpty()) {
                String payloadPath = (String) modifications.get(0).get("path");
                System.out.println("Page updated at path: " + payloadPath);

                Map<String, Object> jobProperties = new HashMap<>();
                jobProperties.put("payload", payloadPath);

                jobManager.addJob("content/replication/job", jobProperties);
                System.out.println("Job added for path: " + payloadPath);
            } else {
                System.out.println("No modifications detected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

