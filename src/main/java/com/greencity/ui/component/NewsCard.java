package com.greencity.ui.component;

import com.greencity.ui.modal.auth.AuthModal;
import com.greencity.ui.page.news.NewsDetailPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NewsCard extends BaseComponent {
    private final boolean gallery;

    private final By title = By.cssSelector("h3");
    private final By galleryText = By.cssSelector(".list-text");
    private final By listText = By.cssSelector(".eco-news_list-content-text");
    private final By galleryTags = By.cssSelector(".ul-eco-buttons > span:first-child");
    private final By listTags = By.cssSelector(".eco-news_list-tag > span:first-child");
    private final By galleryDate = By.cssSelector("p.user-data-text-date.text-nowrap span");
    private final By listDate = By.cssSelector("p.eco-news_data-text-date span.text");
    private final By galleryAuthor = By.cssSelector("span.mw");
    private final By listAuthor = By.cssSelector("p.eco-news_person");
    private final By commentsCount = By.xpath(".//p[contains(@class,'user-data-like')][.//img[@alt='comments']]//span[contains(@class,'numerosity')]");
    private final By likesCount = By.xpath(".//p[contains(@class,'user-data-like')][.//img[@alt='likes']]//span[contains(@class,'numerosity')]");
    private final By moreButton = By.cssSelector("button.secondary-global-button");
    private final By bookmark = By.cssSelector(".favourite-button");
    private final By favoriteMark = By.cssSelector(".favourite-button span.flag-active");

    public NewsCard(WebDriver driver, WebElement root) {
        super(driver, root);
        this.gallery = !root.findElements(By.cssSelector("app-news-list-gallery-view, .list-gallery")).isEmpty();
    }

    public String getTitle() {
        return OptionalFind.text(rootElement, title);
    }

    public String getText() {
        return OptionalFind.text(rootElement, gallery ? galleryText : listText);
    }

    public List<String> getTags() {
        return OptionalFind.texts(rootElement, gallery ? galleryTags : listTags);
    }

    public String getDate() {
        return OptionalFind.text(rootElement, gallery ? galleryDate : listDate);
    }

    public String getAuthor() {
        return OptionalFind.text(rootElement, gallery ? galleryAuthor : listAuthor);
    }

    public int getCommentsCount() {
        return OptionalFind.firstInt(OptionalFind.text(rootElement, commentsCount));
    }

    public int getLikesCount() {
        return OptionalFind.firstInt(OptionalFind.text(rootElement, likesCount));
    }

    public NewsDetailPage open() {
        OptionalFind.click(rootElement, title);
        return new NewsDetailPage(driver);
    }

    public NewsDetailPage openMore() {
        OptionalFind.click(rootElement, moreButton);
        return new NewsDetailPage(driver);
    }

    public void toggleFavorite() {
        OptionalFind.click(rootElement, bookmark);
    }

    public boolean isFavorite() {
        return OptionalFind.displayed(rootElement, favoriteMark) != null;
    }

    public AuthModal openBookmark() {
        String newsListUrl = driver.getCurrentUrl();
        OptionalFind.click(rootElement, bookmark);
        AuthModal modal = new AuthModal(driver).waitUntilSignInOpen();
        if (!"Welcome back!".equals(modal.getTitle())) {
            throw new IllegalStateException(modal.getTitle());
        }
        if (!driver.getCurrentUrl().equals(newsListUrl)) {
            throw new IllegalStateException(driver.getCurrentUrl());
        }
        return modal;
    }
}
