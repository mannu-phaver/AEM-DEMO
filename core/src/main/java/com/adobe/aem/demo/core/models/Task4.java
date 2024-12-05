package com.adobe.aem.demo.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Task4 {

    @ValueMapValue
    private String fname;
    @ValueMapValue
    private String pathfield;

    public String getFname() {
        return fname;
    }

    public String getPathfield() {
        return pathfield;
    }

}
