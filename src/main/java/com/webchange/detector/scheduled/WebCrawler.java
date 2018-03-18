package com.webchange.detector.scheduled;

import com.webchange.detector.constant.CrawlItemStatus;
import com.webchange.detector.model.CrawlItem;
import com.webchange.detector.repository.CrawlItemRepository;
import com.webchange.detector.service.WebsiteParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class WebCrawler {
    private static final Logger logger = LoggerFactory.getLogger(WebCrawler.class);

    @Autowired
    private CrawlItemRepository crawlItemRepository;

    @Autowired
    private WebsiteParser parser;

    @Value("${crawler.retryDelay}")
    private int retryDelay;

    @Scheduled(fixedDelayString = "${crawler.delay}") // interval between invocations measured from the completion of the task
    public void crawlItems() {
        CrawlItem crawlItem = crawlItemRepository.findNextToCrawl(retryDelay);
        if (crawlItem == null) {
            logger.info("Nothing to crawl");
            return;
        }

        logger.info("Crawling item: {}", crawlItem);

        crawlItem.incrementStats();
        crawlItemRepository.save(crawlItem);

        Document websiteData = loadWebsiteData(crawlItem.getUrl());
        if (websiteData == null) {
            return;
        }

        Boolean found = parser.checkWebsiteForContent(websiteData, crawlItem.getContent(), crawlItem.getSelector());
        if (!found) {
            return;
        }

        logger.info("Content found");

        crawlItem.setStatus(CrawlItemStatus.FOUND.toString());
        crawlItemRepository.save(crawlItem);
    }

    private Document loadWebsiteData(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            logger.error("Failed to read url: IOException: " + e.getMessage());
            return null;
        }
    }
}
