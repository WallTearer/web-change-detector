package com.webchange.detector.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebsiteParserTest {

    final private String websiteHtml = "<html>" +
            "   <head>" +
            "      <title>some title</title>" +
            "   </head>" +
            "   <body>" +
            "      <div id='wrapper' class='container'>" +
            "         Lorem ipsum dolor sit amet," +
            "         <span class='inner'>" +
            "            consectetur adipiscing elit," +
            "         </span>" +
            "      </div>" +
            "      sed do eiusmod tempor" +
            "   </body>" +
            "</html>";

    @Autowired
    WebsiteParser parser;

    @Test
    public void testContentFound() {
        Document websiteData = Jsoup.parse(websiteHtml);
        assertEquals(true, parser.checkWebsiteForContent(websiteData, "ipSum DoloR sIt", null));
    }

    @Test
    public void testContentNotFound() {
        Document websiteData = Jsoup.parse(websiteHtml);
        assertEquals(false, parser.checkWebsiteForContent(websiteData, "not there", null));
    }

    @Test
    public void testSelectorFound() {
        Document websiteData = Jsoup.parse(websiteHtml);
        assertEquals(true, parser.checkWebsiteForContent(websiteData, null, "#wrapper.container .inner"));
    }

    @Test
    public void testSelectorNotFound() {
        Document websiteData = Jsoup.parse(websiteHtml);
        assertEquals(false, parser.checkWebsiteForContent(websiteData, null, "#not.there"));
    }

    @Test
    public void testContentWithSelectorFound() {
        Document websiteData = Jsoup.parse(websiteHtml);
        assertEquals(true, parser.checkWebsiteForContent(websiteData, "conSecTetur adipIscing", "#wrapper.container .inner"));
    }

    @Test
    public void testSelectorExistsButContentNotFound() {
        Document websiteData = Jsoup.parse(websiteHtml);
        assertEquals(false, parser.checkWebsiteForContent(websiteData, "not there", "#wrapper.container .inner"));
    }

    @Test
    public void testContentExistsButSelectorNotFound() {
        Document websiteData = Jsoup.parse(websiteHtml);
        assertEquals(false, parser.checkWebsiteForContent(websiteData, "conSecTetur adipIscing", "#not.there"));
    }
}
