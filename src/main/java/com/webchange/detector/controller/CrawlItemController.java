package com.webchange.detector.controller;

import com.webchange.detector.form.CrawlItemForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CrawlItemController {

    private static final Logger logger = LoggerFactory.getLogger(CrawlItemController.class);

    @GetMapping("/new")
    public String newCrawlItem(Model model) {
        logger.debug("Rendering form for the new crawl item");

        model.addAttribute(new CrawlItemForm());

        return "newCrawlItem";
    }

    @PostMapping("/new")
    public String postNewCrawlItem(@ModelAttribute CrawlItemForm crawlItemForm) {
        return "newCrawlItemResult";
    }
}
