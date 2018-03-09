package com.webchange.detector.scheduled;

import com.webchange.detector.constant.CrawlItemStatus;
import com.webchange.detector.model.CrawlItem;
import com.webchange.detector.model.User;
import com.webchange.detector.repository.CrawlItemRepository;
import com.webchange.detector.service.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Mailer {
    private static final Logger logger = LoggerFactory.getLogger(Mailer.class);

    @Autowired
    private CrawlItemRepository crawlItemRepository;

    @Autowired
    private EmailSender emailSender;

    @Scheduled(fixedDelayString = "${mailer.delay}") // interval between invocations measured from the completion of the task
    public void sendMail() {
        CrawlItem crawlItem = crawlItemRepository.findNextToSendEmail();
        if (crawlItem == null) {
            logger.info("No emails to send");
            return;
        }

        crawlItem.incrementMailAttempts();
        crawlItemRepository.save(crawlItem);

        User user = crawlItem.getUser();
        String receiverEmail = user.getEmail();

        String subject = "Change on the website was detected";
        // TODO: render this with a proper templating engine
        // TODO: conditionally output requested content/selector in the email
        String body = "Hi there.\n\nWe're happy to let you know that your content appeared on the "
                      + crawlItem.getUrl() + "\n\nKind regards,\nWeb Change Detector Team";

        boolean isSent = emailSender.send(receiverEmail, subject, body);

        logger.info("Sending email to {} {}", receiverEmail, isSent ? "succeeded" : "failed");

        if (!isSent) {
            return;
        }

        crawlItem.setStatus(CrawlItemStatus.NOTIFIED.toString());
        crawlItemRepository.save(crawlItem);
    }
}
