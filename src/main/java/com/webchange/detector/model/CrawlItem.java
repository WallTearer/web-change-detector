package com.webchange.detector.model;

import com.webchange.detector.constant.CrawlItemStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class CrawlItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    private User user;

    @NotEmpty
    private String url;

    private String selector;

    private String content;

    @NotEmpty
    private String status = CrawlItemStatus.NEW.toString();

    private Integer crawlAttempts = 0;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private Date lastCrawlAt;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void incrementStats() {
        crawlAttempts++;
        lastCrawlAt = Timestamp.valueOf(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return String.format(
            "(%d, %d, %s, %s, %s, %s)",
            id,
            crawlAttempts,
            url,
            content,
            selector,
            lastCrawlAt == null ? "null" : lastCrawlAt.toString()
        );
    }
}
