package com.greencity.ui.page.econews.econews_parts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

// Блок схожих новин під відкритою статтею
public class EcoNewsWidgetComponent {
    private final WebDriver driver;
    private final WebElement parent;

    // Корінь блока схожих новин.
    public final By root = By.cssSelector("app-eco-news-widget");
    // Підпис блока.
    public final By title = By.cssSelector("app-eco-news-widget p");
    // Картка рекомендованої новини.
    public final By cards = By.cssSelector("app-eco-news-widget app-news-list-gallery-view");

    public EcoNewsWidgetComponent(WebDriver driver) {
        this(driver, null);
    }

    public EcoNewsWidgetComponent(WebDriver driver, WebElement parent) {
        this.driver = driver;
        this.parent = parent;
    }

    // Читає підпис блока схожих новин.
    public String getTitle() {
        return Dom.text(context(), title);
    }

    // Збирає картки схожих новин.
    public List<NewsCard> getCards() {
        List<NewsCard> values = new ArrayList<>();
        for (WebElement card : Dom.displayedAll(context(), cards)) {
            values.add(new NewsCard(driver, card));
        }
        return values;
    }

    private org.openqa.selenium.SearchContext context() {
        return Dom.context(driver, parent);
    }
}
