// package com.adobe.aem.demo.core.schedulers;

// // public class hi {

// // }
// import com.day.cq.workflow.WorkflowException;
// import com.day.cq.workflow.WorkflowService;
// import com.day.cq.workflow.WorkflowSession;
// import com.day.cq.workflow.exec.Workflow;

// import org.apache.sling.api.resource.ResourceResolverFactory;
// import org.apache.sling.commons.scheduler.ScheduleOptions;
// import org.apache.sling.commons.scheduler.Scheduler;
// import org.osgi.framework.Constants;
// import org.osgi.service.component.annotations.Activate;
// import org.osgi.service.component.annotations.Component;
// import org.osgi.service.component.annotations.Deactivate;
// import org.osgi.service.component.annotations.Modified;
// import org.osgi.service.component.annotations.Reference;
// import org.osgi.service.metatype.annotations.Designate;
// import com.adobe.aem.demo.core.configs.WorkflowStatusConfiguration;
// import com.adobe.aem.demo.core.services.EmailService;
// // import com.adobe.aem.demo.core.services.impl.ResourceResolverService;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import javax.jcr.Session;
// import java.time.LocalDateTime;

// // import static org.redquark.aem.tutorials.core.constants.AppConstants.EQUALS;
// // import static org.redquark.aem.tutorials.core.constants.AppConstants."\n";
// import static com.adobe.aem.demo.core.schedulers.WorkflowStatusScheduler.NAME;

// @Component(
//         immediate = true,
//         service = Runnable.class,
//         property = {
//                 Constants.SERVICE_ID + "=" + NAME
//         }
// )
// @Designate(ocd = WorkflowStatusConfiguration.class)
// public class WorkflowStatusScheduler implements Runnable {

//     protected static final String NAME = "Workflow Status Scheduler";

//     private static final String TAG = WorkflowStatusScheduler.class.getSimpleName();
//     private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowStatusScheduler.class);
//     @Reference
//     Scheduler scheduler;
//     @Reference
//     WorkflowService workflowService;
//     @Reference
//     ResourceResolverFactory resourceResolverService;
//     @Reference
//     EmailService emailService;
//     private String schedulerName;
//     private String toEmail;
//     private String ccEmail;
//     private String fromEmail;
//     private String subject;

//     @Activate
//     protected void activate(WorkflowStatusConfiguration configuration) {
//         LOGGER.debug("{}: initializing properties for scheduler", TAG);
//         this.schedulerName = configuration.schedulerName();
//         LOGGER.debug("{}: name of the scheduler: {}", TAG, schedulerName);
//         // Details for email
//         this.toEmail = configuration.toEmail();
//         this.ccEmail = configuration.ccEmail();
//         this.fromEmail = configuration.fromEmail();
//         this.subject = configuration.subject();
//     }

//     @Modified
//     protected void modified(WorkflowStatusConfiguration configuration) {
//         LOGGER.info("{}: modifying scheduler with name: {}", TAG, schedulerName);
//         // Remove the scheduler registered with old configuration
//         removeScheduler(configuration);
//         // Add the scheduler registered with new configuration
//         addScheduler(configuration);
//     }

//     @Deactivate
//     protected void deactivate(WorkflowStatusConfiguration configuration) {
//         LOGGER.debug("{}: removing scheduler: {}", TAG, schedulerName);
//         removeScheduler(configuration);
//     }

//     private void addScheduler(WorkflowStatusConfiguration configuration) {
//         // Check if the scheduler has enable flag set to true
//         if (configuration.enabled()) {
//             LOGGER.info("{}: scheduler: {} is enabled", TAG, schedulerName);
//             // Configure the scheduler to use cron expression and some other properties
//             ScheduleOptions scheduleOptions = scheduler.EXPR(configuration.cronExpression());
//             scheduleOptions.name(schedulerName);
//             scheduleOptions.canRunConcurrently(false);
//             // Scheduling the job
//             scheduler.schedule(this, scheduleOptions);
//             LOGGER.info("{}: scheduler {} is added", TAG, schedulerName);
//         } else {
//             LOGGER.info("{}: scheduler {} is disabled", TAG, schedulerName);
//             removeScheduler(configuration);
//         }
//     }

//     private void removeScheduler(WorkflowStatusConfiguration configuration) {
//         LOGGER.info("{}: removing scheduler {}", TAG, schedulerName);
//         scheduler.unschedule(configuration.schedulerName());
//     }

//     private String getWorkflowStatus() throws Exception{
//         // This string will store the status for all workflows and other data
//         StringBuilder workflowDetails = new StringBuilder();
//         try {
//             // Get the JCR session
//             Session session = resourceResolverService.getThreadResourceResolver().adaptTo(Session.class);
//             // Get the workflow session
//             WorkflowSession workflowSession = workflowService.getWorkflowSession(session);
//             // States by which we want to query the workflows
//             String[] states = {"RUNNING", "COMPLETED"};
//             // Get the list of all the workflows by states
//             Workflow[] workflows = workflowSession.getWorkflows(states);
//             // Loop through all the workflows
//             for (Workflow workflow : workflows) {
//                 workflowDetails
//                         .append("ID: ")
//                         .append(workflow.getId())
//                         .append("\n")
//                         .append("Payload: ")
//                         .append(workflow.getWorkflowData().getPayload())
//                         .append("\n")
//                         .append("State: ")
//                         .append(workflow.getState())
//                         .append("\n");
//             }
//         } catch (WorkflowException e) {
//             LOGGER.error("{}: exception occurred: {}", TAG, e.getMessage());
//         }
//         return workflowDetails.toString();
//     }

//     @Override
//     public void run() {
//         // Getting the workflow status
//         String workflowStatus = getWorkflowStatus();
//         // Make the content ready
//         String content = "Hi, " + "\n" + "Following is the workflow status at: " + LocalDateTime.now() + "\n" + workflowStatus;
//         // Send emails
//         emailService.sendEmail(toEmail, ccEmail, fromEmail, subject, content);
//         LOGGER.info("{}: workflow status email is sent", TAG);
//     }
// }


package com.adobe.aem.demo.core.schedulers;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowService;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.Workflow;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import com.adobe.aem.demo.core.configs.WorkflowStatusConfiguration;
import com.adobe.aem.demo.core.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jcr.Session;
import java.time.LocalDateTime;

@Component(
        immediate = true,
        service = Runnable.class,
        property = {
                Constants.SERVICE_ID + "=" + WorkflowStatusScheduler.NAME
        }
)
@Designate(ocd = WorkflowStatusConfiguration.class)
public class WorkflowStatusScheduler implements Runnable {

    protected static final String NAME = "Workflow Status Scheduler";
    private static final String TAG = WorkflowStatusScheduler.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowStatusScheduler.class);

    @Reference
    private Scheduler scheduler;

    @Reference
    private WorkflowService workflowService;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private EmailService emailService;

    private String schedulerName;
    private String toEmail;
    private String ccEmail;
    private String fromEmail;
    private String subject;

    @Activate
    protected void activate(WorkflowStatusConfiguration configuration) {
        LOGGER.info("{}: Activating scheduler with configuration", TAG);
        this.schedulerName = configuration.schedulerName();
        this.toEmail = configuration.toEmail();
        this.ccEmail = configuration.ccEmail();
        this.fromEmail = configuration.fromEmail();
        this.subject = configuration.subject();
        addScheduler(configuration);
    }

    @Modified
    protected void modified(WorkflowStatusConfiguration configuration) {
        LOGGER.info("{}: Modifying scheduler configuration", TAG);
        removeScheduler(configuration);
        addScheduler(configuration);
    }

    @Deactivate
    protected void deactivate(WorkflowStatusConfiguration configuration) {
        LOGGER.info("{}: Deactivating scheduler", TAG);
        removeScheduler(configuration);
    }

    private void addScheduler(WorkflowStatusConfiguration configuration) {
        if (configuration.enabled()) {
            LOGGER.info("{}: Enabling scheduler: {}", TAG, schedulerName);
            ScheduleOptions scheduleOptions = scheduler.EXPR(configuration.cronExpression());
            scheduleOptions.name(schedulerName);
            scheduleOptions.canRunConcurrently(false);
            scheduler.schedule(this, scheduleOptions);
            LOGGER.info("{}: Scheduler added: {}", TAG, schedulerName);
        } else {
            LOGGER.warn("{}: Scheduler is disabled: {}", TAG, schedulerName);
        }
    }

    private void removeScheduler(WorkflowStatusConfiguration configuration) {
        LOGGER.info("{}: Removing scheduler: {}", TAG, schedulerName);
        scheduler.unschedule(configuration.schedulerName());
    }

    private String getWorkflowStatus() {
        StringBuilder workflowDetails = new StringBuilder();
        try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(null)) {
            Session session = resourceResolver.adaptTo(Session.class);
            WorkflowSession workflowSession = workflowService.getWorkflowSession(session);

            String[] states = {"RUNNING", "COMPLETED"};
            Workflow[] workflows = workflowSession.getWorkflows(states);

            for (Workflow workflow : workflows) {
                workflowDetails.append("ID: ")
                        .append(workflow.getId())
                        .append("\n")
                        .append("Payload: ")
                        .append(workflow.getWorkflowData().getPayload())
                        .append("\n")
                        .append("State: ")
                        .append(workflow.getState())
                        .append("\n");
            }
        } catch (WorkflowException e) {
            LOGGER.error("{}: Error retrieving workflow status: {}", TAG, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("{}: Unexpected error: {}", TAG, e.getMessage());
        }
        return workflowDetails.toString();
    }

    @Override
    public void run() {
        try {
            String workflowStatus = getWorkflowStatus();
            String content = "Hi,\nFollowing is the workflow status at: " + LocalDateTime.now() + "\n" + workflowStatus;
            emailService.sendEmail(toEmail, ccEmail, fromEmail, subject, content);
            LOGGER.info("{}: Workflow status email sent successfully", TAG);
        } catch (Exception e) {
            LOGGER.error("{}: Error in scheduler execution: {}", TAG, e.getMessage());
        }
    }
}

