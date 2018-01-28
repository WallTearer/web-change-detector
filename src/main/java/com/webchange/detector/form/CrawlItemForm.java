package com.webchange.detector.form;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

public class CrawlItemForm {

    @NotEmpty
    private String url;

    @URL
    private String selector;

    @NotEmpty
    private String content;

    @NotEmpty
    private String email;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
