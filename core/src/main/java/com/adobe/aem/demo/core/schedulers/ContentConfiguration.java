package com.adobe.aem.demo.core.schedulers;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "ContentConfiguration", description = "about the content")

public @interface ContentConfiguration {

    @AttributeDefinition(name = "schedulerName", description = "about the content", defaultValue = "contentExpirydate")
    public String schedulerName();// thses are properties.

    @AttributeDefinition(name = "schedulerExpression", description = "about the SchedulerExpression", defaultValue = "*/2 * * * * ?")
    public String schedulerExpression();

    @AttributeDefinition(name = "scheduler_concurrent", description = "about the scheduler_concurrent")
    boolean scheduler_concurrent();

    @AttributeDefinition(name = "scheduler_enabled", description = "about the enabled", defaultValue = "true")
    public boolean enabled();

}
