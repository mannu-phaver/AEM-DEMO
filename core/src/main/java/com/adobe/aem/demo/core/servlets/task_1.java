package com.adobe.aem.demo.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import com.google.gson.JsonObject;

@Component(
        service = Servlet.class,
        property = {
            Constants.SERVICE_DESCRIPTION + "=Resource Based Servlet Example",
            "sling.servlet.resourceTypes=example/components/content/resourcebased", 
            "sling.servlet.methods=GET", 
            "sling.servlet.extensions=json" 
        }
)
public class task_1 extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("company", "surge software solutions");
        // jsonResponse.addProperty("", "23");
        jsonResponse.addProperty("Intro", "I am Manoj");
        jsonResponse.addProperty("wishes", "helloooo....!");

        response.getWriter().write(jsonResponse.toString());
    }
}


















// jsonResponse.addProperty("resourcePath", request.getResource().getPath());
        // jsonResponse.addProperty("resourceType", request.getResource().getResourceType());
