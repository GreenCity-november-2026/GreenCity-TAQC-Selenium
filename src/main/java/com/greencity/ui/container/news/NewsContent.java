package com.greencity.ui.container.news;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.component.OptionalFind;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NewsContent extends BaseComponent {
    private static final By DETAIL_ROOT = By.cssSelector("app-eco-news-detail");
    private static final By PREVIEW_ROOT = By.cssSelector("app-news-preview-page");

    private final By tags = By.cssSelector(".tags-item");
    private final By title = By.cssSelector(".news-title");
    private final By date = By.cssSelector(".news-info-date");
    private final By author = By.cssSelector(".news-info-author");
    private final By likeButton = By.cssSelector("img.news_like");
    private final By likesCount = By.cssSelector(".numerosity_likes");
    private final By body = By.cssSelector(".news-text-content");
    private final By sourceLink = By.cssSelector("a.source-text, .source-text");

    public NewsContent(WebDriver driver) {
        super(driver, driver.findElement(By.tagName("body")));
    }

    public boolean isPreview() {
        return OptionalFind.displayed(driver, PREVIEW_ROOT) != null
                || driver.getCurrentUrl().contains("/news/preview");
    }

    public String getTitle() {
        return OptionalFind.text(section(), title);
    }

    public List<String> getTags() {
        return OptionalFind.texts(section(), tags);
    }

    public String getDate() {
        return OptionalFind.text(section(), date);
    }

    public String getAuthor() {
        return OptionalFind.text(section(), author);
    }

    public int getLikesCount() {
        return OptionalFind.firstInt(OptionalFind.text(section(), likesCount));
    }

    public boolean isLiked() {
        WebElement button = OptionalFind.displayed(section(), likeButton);
        if (button == null) {
            return false;
        }
        String src = button.getAttribute("src");
        return src != null && src.contains("liked.png");
    }

    public void like() {
        OptionalFind.click(section(), likeButton);
    }

    public String getText() {
        return OptionalFind.text(section(), body);
    }

    public String getSourceUrl() {
        WebElement link = OptionalFind.first(section(), sourceLink);
        if (link == null) {
            return "";
        }
        String href = link.getAttribute("href");
        if (href != null && !href.isBlank()) {
            return href;
        }
        return link.getText() == null ? "" : link.getText().trim();
    }

    private WebElement section() {
        WebElement preview = OptionalFind.displayed(driver, PREVIEW_ROOT);
        if (preview != null) {
            return preview;
        }
        WebElement detail = OptionalFind.displayed(driver, DETAIL_ROOT);
        if (detail != null) {
            return detail;
        }
        return rootElement;
    }
}
