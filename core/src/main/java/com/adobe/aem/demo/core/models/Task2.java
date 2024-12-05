package com.adobe.aem.demo.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Task2 {

    @ValueMapValue
    private String text;

    @ChildResource
    private List<Multi> mul;

    public String getText() {
        return text;
    }

    public List<Multi> getMul() {
        return mul;
    }
}
