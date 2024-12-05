package com.adobe.aem.demo.core.handlers;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = EventHandler.class,
        immediate = true,
        property = {
            EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/ADDED",
            EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/REMOVED",
            EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/CHANGED",
            EventConstants.EVENT_FILTER + "=(path=/content/aem-guides-Project/us/en/*)"
        }
)
public class EventHandlerExample implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EventHandlerExample.class);

    @Activate
    protected void activate() {
        LOG.info("EventHandlerExample component activated.");
    }

    @Override
    public void handleEvent(Event event) {
        LOG.info("Test: ResourceAdded Event handled.");
        LOG.info("Event Topic: {}", event.getTopic());

        LOG.info("Event properties:");
        for (String name : event.getPropertyNames()) {
            LOG.info("Property Name: {}, Property Value: {}", name, event.getProperty(name));
        }
    }
}
