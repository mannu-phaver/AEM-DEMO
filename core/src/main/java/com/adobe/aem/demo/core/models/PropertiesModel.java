package com.adobe.aem.demo.core.models;

// public class PropertiesModel {
// }
// package com.example.core.models;
import java.util.Map;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PropertiesModel {

    @Inject
    private Resource currentResource;

    public Map<String, Object> getAllProperties() {
        ValueMap properties = currentResource.getValueMap();
        return properties;
    }
}
