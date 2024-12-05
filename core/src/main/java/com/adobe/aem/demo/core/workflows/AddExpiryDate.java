package com.adobe.aem.demo.core.workflows;

import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@Component(
        service = WorkflowProcess.class,
        property = {
            "process.label=" + "Task14", // Updated label for better visibility
        // "process.category=Demo Workflows" // Optional category for grouping processes
        }
)
public class AddExpiryDate implements WorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(AddExpiryDate.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) {
        // WorkflowData workflowData = workItem.getWorkflowData();
        // String payloadPath = workflowData.getPayload().toString();
        // LOG.info("Starting workflow process for payload: {}", payloadPath);

        // try (ResourceResolver resolver = getServiceResourceResolver()) {
        //     Resource resource = resolver.getResource(payloadPath);
        //     if (resource != null) {
        //         LOG.info("Resource found at: {}", payloadPath);
        //         Node pageNode = resource.adaptTo(Node.class);
        //         // Ensure we are targeting the correct node
        //         if (pageNode != null) {
        //             if (pageNode.hasNode("jcr:content")) {
        //                 Node contentNode = pageNode.getNode("jcr:content");
        //                 addExpiryProperty(contentNode);
        //                 resolver.commit();
        //                 LOG.info("Expiry property added to jcr:content node at: {}", payloadPath);
        //             } else {
        //                 LOG.warn("No jcr:content node found under: {}", payloadPath);
        //             }
        //         } else {
        //             LOG.warn("No node found for resource at: {}", payloadPath);
        //         }
        //     } else {
        //         LOG.warn("No resource found at: {}", payloadPath);
        //     }
        // } catch (Exception e) {
        //     LOG.error("Error processing workflow for payload: {}", payloadPath, e);
        // }
    }

    // private void addExpiryProperty(Node node) throws RepositoryException {
    //     Calendar currentTime = Calendar.getInstance();
    //     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    //     String expiryDate = sdf.format(currentTime.getTime());
    //     node.setProperty("expiry", expiryDate);
    //     LOG.info("Expiry property set to: {}", expiryDate);
    // }
    // private ResourceResolver getServiceResourceResolver() throws LoginException {
    //     return resourceResolverFactory.getServiceResourceResolver(
    //             Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "manoj"));
    // }
}
