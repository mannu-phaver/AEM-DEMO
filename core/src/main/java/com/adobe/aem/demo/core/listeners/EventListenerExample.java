package com.adobe.aem.demo.core.listeners;

import javax.jcr.RepositoryException;
import javax.jcr.Session;  // Use javax.jcr.Session
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;

import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = EventListener.class, immediate = true)
public class EventListenerExample implements EventListener {

    private static final Logger LOG = LoggerFactory.getLogger(EventListenerExample.class);

    private Session session;

    @Reference
    SlingRepository slingrepo;

    @Activate
    public void activate() throws Exception {
        try {

            session = slingrepo.loginService("manoj", null);
            session.getWorkspace().getObservationManager().addEventListener(
                    this, //handler
                    Event.NODE_ADDED | Event.PROPERTY_ADDED, //
                    "/content/aem-guides-Project/us/en",
                    true,
                    null,
                    null,
                    true
            );
        } catch (RepositoryException e) {
            LOG.error("Error while adding event listener: {}", e.getMessage(), e);
        }
    }

    @Override
    public void onEvent(EventIterator eventIterator) {
        try {
            while (eventIterator.hasNext()) {
                Event event = eventIterator.nextEvent();
                LOG.info("Event Path: {}", event.getPath());
            }
        } catch (Exception e) {
            LOG.error("Error while processing events: {}", e.getMessage(), e);
        }
    }
}
