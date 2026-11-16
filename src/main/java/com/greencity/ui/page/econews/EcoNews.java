package com.greencity.ui.page.econews;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

// Спільна основа сторінок еко-новин. Зберігає драйвер і кореневий елемент якщо він наявний
public class EcoNews extends BasePage {
    protected final WebElement webElement;

    public EcoNews(WebDriver driver) {
        this(driver, null);
    }

    public EcoNews(WebDriver driver, WebElement parent) {
        super(driver);
        this.webElement = parent;
    }

    // Повертає область пошуку локаторів.
    protected SearchContext context() {
        return webElement != null ? webElement : driver;
    }

    // Знаходить усі елементи за локатором у поточній області пошуку.
    protected List<WebElement> find(By locator) {
        return context().findElements(locator);
    }
}
