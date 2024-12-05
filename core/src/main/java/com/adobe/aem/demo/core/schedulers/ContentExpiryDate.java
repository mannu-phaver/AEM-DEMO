package com.adobe.aem.demo.core.schedulers;

import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Runnable.class, enabled = true, immediate = true)
@Designate(ocd = ContentConfiguration.class)
public class ContentExpiryDate implements Runnable {
    // logger files are used to check whther the bundle is working fine or not.

    private static final Logger log = LoggerFactory.getLogger(ContentExpiryDate.class);
    // for predefind secheduler api
    @Reference
    Scheduler scheduler;

    @Override
    public void run() {
        //to display general message.
        log.info("ContentExpiryDate run() executing");

    }

    @Activate
    public void activate(ContentConfiguration config) {
        updateExpiryDates(config);
    }

    @Deactivate
    public void deActivate(ContentConfiguration config) {
        updateExpiryDates(config);

    }

    @Modified
    public void modified(ContentConfiguration config) {
        updateExpiryDates(config);

    }
    // custom method

    public void updateExpiryDates(ContentConfiguration config) {
        //if scheduler is enabled then only it should execute.

        if (config.enabled()) {

            //values were give as input to the method based on expression
            ScheduleOptions options = scheduler.EXPR(config.schedulerExpression());
            options.canRunConcurrently(false);
            options.name(config.schedulerName());
            scheduler.schedule(this, options);
        } else {

            // remove time alloted based on name.
            scheduler.unschedule(config.schedulerName());
        }

    }

}
