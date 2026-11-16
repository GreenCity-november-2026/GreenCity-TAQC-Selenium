package com.greencity.ui.page.econews.econews_parts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// Блок коментарів унизу сторінки новини.
public class CommentsContainerComponent {
    private final WebDriver driver;
    private final WebElement parent;

    // Корінь блока коментарів.
    public final By root = By.cssSelector("app-comments-container");
    // Число коментарів новини.
    public final By totalCount = By.cssSelector("app-comments-container #total-count");

    public CommentsContainerComponent(WebDriver driver) {
        this(driver, null);
    }

    public CommentsContainerComponent(WebDriver driver, WebElement parent) {
        this.driver = driver;
        this.parent = parent;
    }

    // Читає кількість коментарів
    public int getCount() {
        return Dom.firstInt(Dom.text(context(), totalCount));
    }

    private org.openqa.selenium.SearchContext context() {
        return Dom.context(driver, parent);
    }
}
