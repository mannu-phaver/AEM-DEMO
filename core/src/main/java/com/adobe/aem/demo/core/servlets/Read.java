package com.adobe.aem.demo.core.servlets;

import java.io.IOException;
import java.security.SecureRandom;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

@Component(
        service = Servlet.class,
        property = {
            "sling.servlet.paths=/bin/demo/randomWordServlet", // Bind to this specific path
            "sling.servlet.methods=GET" // Allow only GET requests
        }
)
public class Read extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        // Set response type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Get the parameter from the request
        String sessionParam = request.getParameter("session");
        String randomParam = request.getParameter("random");

        // Generate a random 6-letter word if parameter is present
        String randomWord = null;
        if (sessionParam != null) {
            randomWord = generateRandomWord();
        } else if (randomParam != null) {
            randomWord = generateRandomWord();
        }

        // Create JSON response
        if (randomWord != null) {
            response.getWriter().write("{\"randomWord\": \"" + randomWord + "\"}");
        } else {
            response.getWriter().write("{\"error\": \"Invalid parameter. Use 'session' or 'random'.\"}");
        }
    }

    // Helper method to generate a random 6-letter word
    private String generateRandomWord() {
        final String alphabet = "abcdefghijklmnopqrstuvwxyz";
        SecureRandom random = new SecureRandom();
        StringBuilder randomWord = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(alphabet.length());
            randomWord.append(alphabet.charAt(index));
        }

        return randomWord.toString();
    }
}
