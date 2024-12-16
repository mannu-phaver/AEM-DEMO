package com.adobe.aem.demo.core.models;

import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;


@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Mulit4 {
    @ValueMapValue
    private String additionaltext;
    @ValueMapValue
    private String pathfield;

    public String getAdditionaltext() {
        return additionaltext;
    }

    public String getPathfield() {
        return pathfield;
    }

}
