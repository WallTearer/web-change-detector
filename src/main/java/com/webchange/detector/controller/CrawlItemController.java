package com.webchange.detector.controller;

import com.webchange.detector.form.CrawlItemForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class CrawlItemController {

    private static final Logger logger = LoggerFactory.getLogger(CrawlItemController.class);

    @GetMapping("/new")
    public String newCrawlItem(CrawlItemForm crawlItemForm) {
        logger.info("Rendering form for the new crawl item");

        return "newCrawlItem";
    }

    @PostMapping("/new")
    public String postNewCrawlItem(@Valid CrawlItemForm crawlItemForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "newCrawlItem";
        }

        return "newCrawlItemResult";
    }
}
