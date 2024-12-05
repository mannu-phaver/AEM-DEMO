package com.adobe.aem.demo.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Multi2 {

    @ValueMapValue
    private String icon1;

    @ValueMapValue
    private String mobileicon1;

    @ChildResource
    private List<Nest1> nested;

    public List<Nest1> getNested() {
        return nested;
    }

    public String getIcon1() {
        return icon1;
    }

    public String getMobileicon1() {
        return mobileicon1;
    }

}
