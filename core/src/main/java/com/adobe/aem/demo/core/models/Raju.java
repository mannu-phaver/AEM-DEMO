package com.adobe.aem.demo.core.models;


import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class Raju {
    @ValueMapValue
    private String text;

    @ValueMapValue
    private String imagepath;

    @ChildResource
    private List<Mulit4> multifield;

    public String getText() {
        return text;
    }

    public List<Mulit4> getMultifield() {
        return multifield;
    }

    public String getImagepath() {
        return imagepath;
    }

}
