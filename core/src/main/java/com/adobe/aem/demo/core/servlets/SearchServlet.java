package com.adobe.aem.demo.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.adobe.aem.demo.core.services.SearchService;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.List;

import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_METHODS;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_PATHS;

@Component(service = Servlet.class, property = {
        Constants.SERVICE_ID + "=SearchServlet",
        SLING_SERVLET_PATHS + "=/bin/demo1/search",
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST
})
public class SearchServlet extends SlingAllMethodsServlet {

    @Reference
    private SearchService searchService;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        try {
            String keyword = request.getParameter("keyword");

            if (keyword == null || keyword.trim().isEmpty()) {
                response.getWriter().println("Error: Keyword parameter is required.");
                return;
            }

            // Call the service to perform the search
            List<String> resultList = searchService.searchByKeyword(keyword);

            if (!resultList.isEmpty()) {
                // Print total results found
                response.getWriter().println("Total results found: " + resultList.size());

                // Format and print the results
                StringBuilder formattedResult = new StringBuilder();
                resultList.forEach(result -> formattedResult.append(result).append("\n"));
                response.getWriter().println(formattedResult.toString());
            } else {
                response.getWriter()
                        .println("No results found for keyword: " + keyword + ". Total results: " + resultList.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
