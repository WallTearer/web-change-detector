package com.webchange.detector.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.AssertTrue;

public class CrawlItemForm {

    @NotEmpty
    @URL
    private String url;

    private String selector;

    private String content;

    @NotEmpty
    @Email
    private String email;

    public String getUrl() {
        return url;
    }

    @AssertTrue(message = "Content or CSS Selector should be given")
    public boolean isSelectorOrContentSet() {
        return hasSelector() || hasContent();
    }

    public boolean hasSelector() {
        return (selector != null && !selector.isEmpty());
    }

    public boolean hasContent() {
        return (content != null && !content.isEmpty());
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
