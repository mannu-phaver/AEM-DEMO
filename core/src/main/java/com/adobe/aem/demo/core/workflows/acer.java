package com.adobe.aem.demo.core.workflows;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@Component(
        service = WorkflowProcess.class,
        property = {
            "process.label=acer" // Ensure this label is unique and meaningful
        }
)
public class acer implements WorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(acer.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        try {
            String payloadType = workItem.getWorkflowData().getPayloadType();
            LOG.info("Payload type: {}", payloadType);

            if ("JCR_PATH".equals(payloadType)) {
                String path = workItem.getWorkflowData().getPayload().toString();
                LOG.info("Payload path: {}", path);
            }
        } catch (Exception e) {
            LOG.error("Error during workflow execution", e);

            // It's crucial to propagate the exception to indicate a failure in the workflow step
            throw new WorkflowException("Error during workflow execution", e);
        }
    }
}
