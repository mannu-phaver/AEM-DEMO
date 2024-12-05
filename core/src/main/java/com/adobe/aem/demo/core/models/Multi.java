package com.adobe.aem.demo.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Multi {

    @ValueMapValue
    private String text1;

    @ValueMapValue
    private String image;

    @ValueMapValue
    private String checkbox;

    @ValueMapValue
    private String country;

    @ChildResource
    private List<Nest> nested;

    public String getText1() {
        return text1;
    }

    public String getImage() {
        return image;
    }

    public String getCheckbox() {
        return checkbox;
    }

    public String getCountry() {
        return country;
    }

    public List<Nest> getNested() {
        return nested;
    }
}
