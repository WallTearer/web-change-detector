package com.webchange.detector.scheduled;

import com.webchange.detector.model.CrawlItem;
import com.webchange.detector.repository.CrawlItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WebCrawler {
    private static final Logger logger = LoggerFactory.getLogger(WebCrawler.class);

    @Autowired
    private CrawlItemRepository crawlItemRepository;

    @Scheduled(fixedDelay = 10000) // interval between invocations measured from the completion of the task
    public void crawlItems() {
        CrawlItem crawlItem = crawlItemRepository.findNextToCrawl();

        logger.info("Crawling item: {}", crawlItem);

        crawlItem.incrementStats();
        crawlItemRepository.save(crawlItem);
    }
}
