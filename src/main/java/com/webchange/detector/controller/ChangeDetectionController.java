package com.webchange.detector.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class ChangeDetectionController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Web Change Detector!";
    }

}