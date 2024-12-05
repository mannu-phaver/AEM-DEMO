package com.adobe.aem.demo.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Task3 {

    @ValueMapValue
    private String fname;
    @ValueMapValue
    private String student;

    public String getFname() {
        return fname;
    }

    public String getStudent() {
        return student;
    }

}
