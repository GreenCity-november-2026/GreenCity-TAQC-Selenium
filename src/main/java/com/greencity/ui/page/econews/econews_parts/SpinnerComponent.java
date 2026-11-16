package com.greencity.ui.page.econews.econews_parts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// Спінер довантаження стрічки, компонент app-spinner.
public class SpinnerComponent {
    private final WebDriver driver;
    private final WebElement parent;

    // Корінь спінера.
    public final By root = By.cssSelector("app-spinner");

    public SpinnerComponent(WebDriver driver) {
        this(driver, null);
    }

    public SpinnerComponent(WebDriver driver, WebElement parent) {
        this.driver = driver;
        this.parent = parent;
    }

    // Повідомляє, чи спінер зараз видимий
    public boolean isDisplayed() {
        return Dom.displayed(context(), root) != null;
    }

    private org.openqa.selenium.SearchContext context() {
        return Dom.context(driver, parent);
    }
}
