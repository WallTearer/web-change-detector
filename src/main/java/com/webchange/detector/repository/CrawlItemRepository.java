package com.webchange.detector.repository;

import com.webchange.detector.model.CrawlItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CrawlItemRepository extends CrudRepository<CrawlItem, Long> {

    @Query(value = "SELECT * FROM crawl_item ORDER BY last_crawl_at ASC, updated_at ASC LIMIT 1", nativeQuery = true)
    CrawlItem findNextToCrawl();
}