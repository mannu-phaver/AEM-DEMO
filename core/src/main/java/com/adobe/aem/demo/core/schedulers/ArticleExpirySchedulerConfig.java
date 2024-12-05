package com.adobe.aem.demo.core.schedulers;

// public @interface ArticleExpirySchedulerConfig {
// }
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Article Expiry Scheduler Configuration")
public @interface ArticleExpirySchedulerConfig {

    @AttributeDefinition(name = "Enable/Disable Scheduler")
    boolean enable() default true;

    @AttributeDefinition(name = "Scheduler Name")
    String schedulerName() default "article-expiry-scheduler";

    @AttributeDefinition(name = "Scheduler Cron Expression")
    String schedulerExpression() default "0 0 * * * ?"; // Default: Every hour

    @AttributeDefinition(name = "Path to Root Page")
    String path() default "/content";
}
