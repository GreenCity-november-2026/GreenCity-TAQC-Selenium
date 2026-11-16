package com.greencity.ui.page.econews.econews_parts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// Лічильник того, скільки новин лишилось у поточній видачі
public class RemainingCountComponent {
    private final WebDriver driver;
    private final WebElement parent;

    // Корінь лічильника.
    public final By root = By.cssSelector("app-remaining-count");
    // Рядок із числом і словом «новин».
    public final By countText = By.cssSelector("app-remaining-count h2");

    public RemainingCountComponent(WebDriver driver) {
        this(driver, null);
    }

    public RemainingCountComponent(WebDriver driver, WebElement parent) {
        this.driver = driver;
        this.parent = parent;
    }

    // Читає число новин із підпису лічильника.
    public int getCount() {
        return Dom.firstInt(Dom.text(context(), countText));
    }

    private org.openqa.selenium.SearchContext context() {
        return Dom.context(driver, parent);
    }
}
