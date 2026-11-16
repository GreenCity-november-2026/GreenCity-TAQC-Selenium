package com.greencity.ui.page.econews.econews_parts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// Пошук, закладка стрічки і кнопка створення новини над списком.
public class NewsFilterBar {
    private final WebDriver driver;
    private final WebElement parent;

    public final TagFilterComponent tags;

    // Іконка, яка відкриває поле пошуку.
    public final By searchToggle = By.cssSelector("span.search-img");
    // Поле пошуку за назвою новини.
    public final By searchInput = By.cssSelector("input.place-input");
    // Хрестик, який очищує або ховає поле пошуку.
    public final By clearSearchButton = By.cssSelector("img.cross-position[alt='cancel search']");
    // Закладка режиму збережених новин.
    public final By bookmarkButton = By.cssSelector("span.bookmark-img");
    // Кнопка «Створити новину». Видна лише після входу.
    public final By createButton = By.cssSelector("#create-button");

    public NewsFilterBar(WebDriver driver) {
        this(driver, null);
    }

    public NewsFilterBar(WebDriver driver, WebElement parent) {
        this.driver = driver;
        this.parent = parent;
        this.tags = new TagFilterComponent(driver, parent);
    }

    // Вводить текст у пошук стрічки.
    public void search(String text) {
        if (Dom.displayed(context(), searchInput) == null) {
            Dom.click(context(), searchToggle);
        }
        WebElement input = Dom.displayed(context(), searchInput);
        input.clear();
        input.sendKeys(text);
    }

    // Очищує пошук стрічки.
    public void clearSearch() {
        Dom.click(context(), clearSearchButton);
    }

    // Читає поточний текст у полі пошуку.
    public String getSearchText() {
        WebElement input = Dom.displayed(context(), searchInput);
        if (input == null) {
            return "";
        }
        String value = input.getAttribute("value");
        return value == null ? "" : value;
    }

    // Увімкнути або вимкнути показ збережених новин.
    public void toggleBookmark() {
        Dom.click(context(), bookmarkButton);
    }

    // Повідомляє, чи увімкнений режим збережених новин.
    public boolean isBookmarkActive() {
        return Dom.hasClass(Dom.displayed(context(), bookmarkButton), "bookmark-img-active");
    }

    // Повідомляє, чи на екрані є кнопка створення новини.
    public boolean isCreateAvailable() {
        return Dom.displayed(context(), createButton) != null;
    }

    // Відкриває форму створення новини.
    public CreateEditNewsPage openCreate() {
        Dom.click(context(), createButton);
        return new CreateEditNewsPage(driver);
    }

    private org.openqa.selenium.SearchContext context() {
        return Dom.context(driver, parent);
    }
}
