package com.adobe.aem.demo.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class,
        immediate = true,
        property = {"sling.servlet.resourceTypes=example/task1122/gi",
            "sling.servlet.extenisons=json",
            "sling.servlet.methods=GET"

        })
public class practice1122 extends SlingAllMethodsServlet {

    @Override
    public void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) throws IOException {

        res.getWriter().write("data is coming");

    }

}
