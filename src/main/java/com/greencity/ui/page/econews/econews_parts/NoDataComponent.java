package com.greencity.ui.page.econews.econews_parts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// Порожній стан стрічки, компонент app-no-data.
public class NoDataComponent {
    private final WebDriver driver;
    private final WebElement parent;

    // Корінь порожнього стану.
    public final By root = By.cssSelector("app-no-data");
    // Заголовок порожнього стану.
    public final By title = By.cssSelector("app-no-data h2");
    // Пояснення, чому список порожній.
    public final By text = By.cssSelector("app-no-data .description__advise p");

    public NoDataComponent(WebDriver driver) {
        this(driver, null);
    }

    public NoDataComponent(WebDriver driver, WebElement parent) {
        this.driver = driver;
        this.parent = parent;
    }

    // Повідомляє, чи на екрані порожній стан.
    public boolean isDisplayed() {
        return Dom.displayed(context(), root) != null;
    }

    // Читає заголовок порожнього стану.
    public String getTitle() {
        return Dom.text(context(), title);
    }

    // Читає пояснення під заголовком порожнього стану.
    public String getText() {
        return Dom.text(context(), text);
    }

    private org.openqa.selenium.SearchContext context() {
        return Dom.context(driver, parent);
    }
}
