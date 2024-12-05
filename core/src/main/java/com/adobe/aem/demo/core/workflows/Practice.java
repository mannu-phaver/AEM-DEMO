package com.adobe.aem.demo.core.workflows;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@Component(service = WorkflowProcess.class, immediate = true,
        property = {
            "process.label=Test Digital Workflow Process"
        })

public class Practice implements WorkflowProcess {

    private static final Logger log = LoggerFactory.getLogger(Practice.class);

    public void execute(WorkItem workItem, WorkflowSession wSession, MetaDataMap mDataMap) {
        String payload = workItem.getWorkflowData().getPayloadType();
        if (payload.equals("JCR_PATH")) {
            log.info("payload {}", payload);
            String path = workItem.getWorkflowData().getPayload().toString();
            log.info("path{}", path);
        }

    }

}
