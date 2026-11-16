package com.greencity.ui.page.econews.econews_parts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

//  Одна картка новини зі списку
public class NewsCard {
    private final WebDriver driver;
    private final WebElement root;
    private final boolean gallery;

    // Заголовок картки.
    private final By title = By.cssSelector("h3");
    // Короткий текст картки в галереї.
    private final By galleryText = By.cssSelector(".list-text");
    // Короткий текст картки в списку.
    private final By listText = By.cssSelector(".eco-news_list-content-text");
    // Назва тега в галереї, без роздільника між тегами.
    private final By galleryTags = By.cssSelector(".ul-eco-buttons > span:first-child");
    // Назва тега в списку, без роздільника між тегами.
    private final By listTags = By.cssSelector(".eco-news_list-tag > span:first-child");
    // Дата публікації в галереї.
    private final By galleryDate = By.cssSelector("p.user-data-text-date.text-nowrap span");
    // Дата публікації в списку.
    private final By listDate = By.cssSelector("p.eco-news_data-text-date span.text");
    // Ім'я автора в галереї.
    private final By galleryAuthor = By.cssSelector("span.mw");
    // Ім'я автора в списку.
    private final By listAuthor = By.cssSelector("p.eco-news_person");
    // Кількість коментарів. Є тільки на картці галереї.
    private final By commentsCount = By.xpath(".//p[contains(@class,'user-data-like')][.//img[@alt='comments']]//span[contains(@class,'numerosity')]");
    // Кількість вподобань. Є тільки на картці галереї.
    private final By likesCount = By.xpath(".//p[contains(@class,'user-data-like')][.//img[@alt='likes']]//span[contains(@class,'numerosity')]");
    // Кнопка «Більше» на картці списку.
    private final By moreButton = By.cssSelector("button.secondary-global-button");
    // Закладка цієї новини.
    private final By bookmark = By.cssSelector(".favourite-button");
    // Позначка, що новина вже в закладках.
    private final By favoriteMark = By.cssSelector(".favourite-button span.flag-active");

    public NewsCard(WebDriver driver, WebElement root) {
        this.driver = driver;
        this.root = root;
        this.gallery = !root.findElements(By.cssSelector("app-news-list-gallery-view, .list-gallery")).isEmpty();
    }

   // Читає заголовок картки.
    public String getTitle() {
        return Dom.text(root, title);
    }

    // Читає короткий текст під заголовком.
    public String getText() {
        return Dom.text(root, gallery ? galleryText : listText);
    }

    // Читає теги, показані на картці.
    public List<String> getTags() {
        return Dom.texts(root, gallery ? galleryTags : listTags);
    }

    // Читає дату публікації з картки.
    public String getDate() {
        return Dom.text(root, gallery ? galleryDate : listDate);
    }

    // Читає ім'я автора з картки.
    public String getAuthor() {
        return Dom.text(root, gallery ? galleryAuthor : listAuthor);
    }

    // Читає кількість коментарів на картці галереї.
    public int getCommentsCount() {
        return Dom.firstInt(Dom.text(root, commentsCount));
    }

    // Читає кількість вподобань на картці галереї.
    public int getLikesCount() {
        return Dom.firstInt(Dom.text(root, likesCount));
    }

    // Відкриває новину кліком по її заголовку.
    public EcoNewsDetailPage open() {
        Dom.click(root, title);
        return new EcoNewsDetailPage(driver);
    }

    // Натискає «Більше» на картці списку.
    public EcoNewsDetailPage openMore() {
        Dom.click(root, moreButton);
        return new EcoNewsDetailPage(driver);
    }

    // Ставить або знімає закладку з цієї новини.
    public void toggleFavorite() {
        Dom.click(root, bookmark);
    }

    // Активна закладка на екрані має клас flag-active.
    public boolean isFavorite() {
        return Dom.displayed(root, favoriteMark) != null;
    }
}
