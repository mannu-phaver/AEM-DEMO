package com.adobe.aem.demo.core.models;

// public class enabled {
// }
// package com.example.models;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
public class enabled {

    @ValueMapValue
    private String enabled;  // This will store the enabled state from JCR

    @Inject
    private Resource resource;  // Access to the resource (content fragment) itself

    @PostConstruct
    protected void init() {
        // Check if the content fragment is already enabled
        if ("true".equals(enabled)) {
            // If already enabled, do nothing or handle accordingly
            return;
        } else {
            // If not enabled, enable it and set the 'enabled' flag
            enableContentFragment();
        }
    }

    private void enableContentFragment() {
        // Logic to enable the content fragment
        ModifiableValueMap properties = resource.adaptTo(ModifiableValueMap.class);
        if (properties != null) {
            properties.put("enabled", "true");  // Set enabled flag to true

            // Save the changes to the repository
            try {
                ResourceResolver resourceResolver = resource.getResourceResolver();
                Session session = resourceResolver.adaptTo(Session.class);
                if (session != null) {
                    session.save();  // Save the changes to the repository
                }
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        }
    }
}
