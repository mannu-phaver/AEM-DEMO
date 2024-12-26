package com.adobe.aem.demo.core.services;

// public interface hi {

// }
// package org.redquark.aem.tutorials.core.services;

public interface EmailService {

    void sendEmail(
            String toEmail,
            String ccEmail,
            String fromEmail,
            String subject,
            String content
    );
}