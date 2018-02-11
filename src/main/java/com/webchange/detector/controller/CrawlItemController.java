package com.webchange.detector.controller;

import com.webchange.detector.form.CrawlItemForm;
import com.webchange.detector.model.CrawlItem;
import com.webchange.detector.model.User;
import com.webchange.detector.repository.CrawlItemRepository;
import com.webchange.detector.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class CrawlItemController {

    private static final Logger logger = LoggerFactory.getLogger(CrawlItemController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CrawlItemRepository crawlItemRepository;

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

        User user = userRepository.findByEmail(crawlItemForm.getEmail());
        if (user == null) {
            user = new User();
            user.setEmail(crawlItemForm.getEmail());
            userRepository.save(user);
        }

        CrawlItem crawlItem = new CrawlItem();
        crawlItem.setUser(user);
        crawlItem.setUrl(crawlItemForm.getUrl());
        crawlItem.setSelector(crawlItemForm.getSelector());
        crawlItem.setContent(crawlItemForm.getContent());
        crawlItemRepository.save(crawlItem);

        return "newCrawlItemResult";
    }
}
