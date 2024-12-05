package com.adobe.aem.demo.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.installer.api.info.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        resourceType = "/apps/demo1/components/example")
public class Example {

    @ValueMapValue
    private String fname;
    @ValueMapValue
    private String student;
    @ValueMapValue
    private String Dropdown;
    @ValueMapValue
    private String path;

    public String getFname() {
        return fname;
    }

    public String getStudent() {
        return student;
    }

    public String getDropdown() {
        return Dropdown;
    }

    public String getPath() {
        return path;
    }

}
