package com.webchange.detector.service;

import com.webchange.detector.scheduled.WebCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(WebCrawler.class);

    @Autowired
    private JavaMailSender emailClient;

    public boolean send(String receiverEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiverEmail);
        message.setSubject(subject);
        message.setText(body);

        try {
            emailClient.send(message);
        } catch (Exception e) {
            logger.error("Failed to send email", e);
            return false;
        }

        return true;
    }
}
