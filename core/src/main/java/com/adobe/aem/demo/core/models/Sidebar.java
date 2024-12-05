package com.adobe.aem.demo.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Sidebar {

    @ValueMapValue
    private String logopath;

    @ValueMapValue
    private String logomobileimage;

    @ValueMapValue
    private String logolink;

    @ValueMapValue
    private String checkbox;

    @ValueMapValue
    private String country;

    @ChildResource
    private List<Multi1> mul1;

    @ChildResource
    private List<Multi2> mul2;

    public String getLogopath() {
        return logopath;
    }

    public String getLogomobileimage() {
        return logomobileimage;
    }

    public String getLogolink() {
        return logolink;
    }

    public String getCheckbox() {
        return checkbox;
    }

    public String getCountry() {
        return country;
    }

    public List<Multi1> getMul1() {
        return mul1;
    }

    public List<Multi2> getMul2() {
        return mul2;
    }
}
