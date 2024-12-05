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
        enabled = true,
        immediate = true,
        property = {
            "process.label=Test Digital Workflow Process"
        }
)
public class TestDigital implements WorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(TestDigital.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession wSession, MetaDataMap mDataMap) throws WorkflowException {
        String payload = workItem.getWorkflowData().getPayloadType();
        // LOG.info("payload {}", payload);

        if (payload.equals("JCR_PATH")) {
            LOG.info("payloadType {}", payload);
            String path = workItem.getWorkflowData().getPayload().toString();
            LOG.info("path {}", path);
        }
    }
}
