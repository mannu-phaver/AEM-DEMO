package com.adobe.aem.demo.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class, immediate = true,
        property = {
            "sling.servlet.paths=/bin/example"
        })
public class Prac1 extends SlingAllMethodsServlet {

    public void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) throws IOException {
        res.getWriter().write("hi");
    }

}
