package com.adobe.aem.demo.core.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PropertiesModel {

    @ValueMapValue
    private String pathfield;

    @Inject
    private ResourceResolver resourceResolver;

    public String getPathfield() {
        if (pathfield != null && !pathfield.endsWith("/jcr:content")) {
            return pathfield + "/jcr:content";
        }
        return pathfield;
    }

    public List<Map.Entry<String, String>> getJcrContentPropertiesAsEntries() {
        Map<String, String> propertiesMap = new HashMap<>();

        if (pathfield != null) {
            Resource resource = resourceResolver.resolve(pathfield);

            if (resource != null) {
                Resource jcrContentResource = resource.getChild("jcr:content");

                if (jcrContentResource != null) {
                    ValueMap valueMap = jcrContentResource.getValueMap();

                    for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
                        Object value = entry.getValue();

                        // Exclude or modify date-related properties
                        if (value instanceof Calendar) {
                            Calendar calendar = (Calendar) value;
                            String formattedDate = String.format("%1$td-%1$tm-%1$tY", calendar); // Format as day-month-year (exclude time)
                            propertiesMap.put(entry.getKey(), formattedDate);
                        } else {
                            propertiesMap.put(entry.getKey(), value != null ? value.toString() : "null");
                        }
                    }
                }
            }
        }
        return new ArrayList<>(propertiesMap.entrySet());
    }
}
