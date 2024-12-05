package com.adobe.aem.demo.core.servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

@Component(service = Servlet.class,
        immediate = true,
        property = {
            "sling.servlet.paths=/bin/example/tag"
        })//to regiester
public class Tags2411 extends SlingAllMethodsServlet {

    public void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) throws IOException {

        // to print last element in tags
        // ResourceResolver resolver = req.getResourceResolver();
        // TagManager tagmanager = resolver.adaptTo(TagManager.class);
        // Tag tag = tagmanager.resolve("/content/cq:tags/tag2411/colour");
        // Iterator<Tag> listChildren = tag.listChildren();
        // JsonArrayBuilder jsonarray = Json.createArrayBuilder();
        // JsonObjectBuilder Jsonobject = Json.createObjectBuilder();
        // while (listChildren.hasNext()) {
        //     Tag childtag = listChildren.next();
        //     Jsonobject.add("tittle", childtag.getTitle());
        //     Jsonobject.add("Path", childtag.getPath());
        // }
        // jsonarray.add(Jsonobject);
        // res.getWriter().write(jsonarray.build().toString());
        // to print all the tags.
        ResourceResolver resolver = req.getResourceResolver();
        TagManager tagmanager = resolver.adaptTo(TagManager.class);

        // Resolving the tag
        Tag tag = tagmanager.resolve("/content/cq:tags/tag2411/colour");
        if (tag != null) {
            Iterator<Tag> listChildren = tag.listChildren();
            JsonArrayBuilder jsonArray = Json.createArrayBuilder();

            // Iterating over child tags
            while (listChildren.hasNext()) {
                Tag childTag = listChildren.next();
                JsonObjectBuilder jsonObject = Json.createObjectBuilder();
                jsonObject.add("title", childTag.getTitle());
                jsonObject.add("path", childTag.getPath());
                jsonArray.add(jsonObject);
            }

            // Writing the JSON response
            res.setContentType("application/json");
            res.getWriter().write(jsonArray.build().toString());
        } else {
            res.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("{\"error\":\"Tag not found\"}");
        }
    }

}
