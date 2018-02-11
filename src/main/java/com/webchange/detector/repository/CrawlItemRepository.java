package com.webchange.detector.repository;

import com.webchange.detector.model.CrawlItem;
import org.springframework.data.repository.CrudRepository;

public interface CrawlItemRepository extends CrudRepository<CrawlItem, Long> {
}