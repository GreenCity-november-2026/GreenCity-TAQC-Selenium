package com.greencity.ui.page.econews.econews_parts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// Перемикач вигляду стрічки
public class ChangeViewButtonComponent {
    private final WebDriver driver;
    private final WebElement parent;

    // Корінь перемикача вигляду.
    public final By root = By.cssSelector("app-change-view-button");
    // Кнопка вигляду плиткою.
    public final By galleryButton = By.cssSelector("app-change-view-button span.btn-tiles");
    // Кнопка вигляду списком.
    public final By listButton = By.cssSelector("app-change-view-button span.btn-bars");

    public ChangeViewButtonComponent(WebDriver driver) {
        this(driver, null);
    }

    public ChangeViewButtonComponent(WebDriver driver, WebElement parent) {
        this.driver = driver;
        this.parent = parent;
    }

    // Перемикає стрічку на плитку.
    public void showGallery() {
        Dom.click(context(), galleryButton);
    }

    // Перемикає стрічку на список.
    public void showList() {
        Dom.click(context(), listButton);
    }

    // Повідомляє, чи зараз увімкнена плитка.
    public boolean isGalleryView() {
        return Dom.hasClass(Dom.displayed(context(), galleryButton), "btn-tiles-active");
    }

    private org.openqa.selenium.SearchContext context() {
        return Dom.context(driver, parent);
    }
}
