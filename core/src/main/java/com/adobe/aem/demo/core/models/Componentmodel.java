package com.adobe.aem.demo.core.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Componentmodel {

    @ValueMapValue
    private String textField;

    @ValueMapValue
    private String pathField;

    @ValueMapValue
    private String selectedDate;

    private String message;

    public String getTextField() {
        return textField;
    }

    public String getPathField() {
        return pathField;
    }

    public String getMessage() {
        return message;
    }

    @PostConstruct
    protected void init() {
        if (selectedDate == null || selectedDate.isEmpty()) {
            message = "Invalid Date";
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date selected = sdf.parse(selectedDate);
            Date today = sdf.parse(sdf.format(new Date()));

            // Compare dates without time
            if (selected.before(today)) {
                message = "Component Expired";
            } else {
                message = "Valid";
            }
        } catch (Exception e) {
            message = "Invalid Date";
        }
    }
}
