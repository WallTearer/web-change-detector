package com.webchange.detector.repository;

import com.webchange.detector.model.CrawlItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CrawlItemRepository extends CrudRepository<CrawlItem, Long> {

    @Query(
        value =
            "SELECT * FROM crawl_item " +
            "WHERE status = 'NEW' " +
            "AND (last_crawl_at IS NULL OR (last_crawl_at < NOW() - INTERVAL :retryDelay MINUTE))" +
            "ORDER BY last_crawl_at ASC, updated_at ASC LIMIT 1",
        nativeQuery = true
    )
    CrawlItem findNextToCrawl(@Param("retryDelay") int retryDelay);

    @Query(value = "SELECT * FROM detector.crawl_item WHERE status = 'FOUND' ORDER BY updated_at ASC LIMIT 1", nativeQuery = true)
    CrawlItem findNextToSendEmail();
}