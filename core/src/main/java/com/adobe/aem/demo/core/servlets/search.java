package com.adobe.aem.demo.core.servlets;

import java.io.IOException;
import java.security.SecureRandom;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class,
        property = {
            "sling.servlet.paths=/bin/demo/randomWordServlet",
            "sling.servlet.methods=GET",
            "sling.servlet.extensions=json"
        })
public class search extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String sessionParam = request.getParameter("session");
        String randomParam = request.getParameter("random");

        String randomWord = null;
        if (sessionParam != null) {
            randomWord = generateRandomWord();
        } else if (randomParam != null) {
            randomWord = generateRandomWord();
        }

        if (randomWord != null) {
            response.getWriter().write("{\"randomWord\": \"" + randomWord + "\"}");
        } else {
            response.getWriter().write("{\"error\": \"Invalid parameter. Use 'session' or 'random'.\"}");
        }
    }

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
