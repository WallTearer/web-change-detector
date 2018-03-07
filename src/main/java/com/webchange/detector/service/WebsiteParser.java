package com.webchange.detector.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class WebsiteParser {

    public Boolean checkWebsiteForContent(Document websiteData, String content, String selector) {
        if (selector == null || selector.isEmpty()) {
            Elements nodesWithContent = websiteData.getElementsContainingOwnText(content);

            return !nodesWithContent.isEmpty();
        }

        Elements nodesWithSelector = websiteData.select(selector);
        if (content == null || content.isEmpty()) {
            return !nodesWithSelector.isEmpty();
        }

        for (Element node : nodesWithSelector) {
            if (!node.getElementsContainingText(content).isEmpty()) {
                return true;
            }
        }

        return false;
    }
}
