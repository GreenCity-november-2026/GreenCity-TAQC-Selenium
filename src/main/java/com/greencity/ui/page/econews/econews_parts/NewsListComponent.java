package com.greencity.ui.page.econews.econews_parts;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

// Стрічка карток новин усередині app-news-list.
public class NewsListComponent {
    private final WebDriver driver;
    private final WebElement parent;

    // Корінь списку новин.
    public final By root = By.cssSelector("app-news-list");
    // Посилання однієї картки. Усередині нього галерея або список і закладка.
    public final By cards = By.cssSelector("ul.list a.link");
    // Місце, до якого треба прокрутити сторінку, щоб підвантажились наступні новини.
    public final By infiniteScroll = By.cssSelector("[infinitescroll]");

    public NewsListComponent(WebDriver driver) {
        this(driver, null);
    }

    public NewsListComponent(WebDriver driver, WebElement parent) {
        this.driver = driver;
        this.parent = parent;
    }

    // Збирає видимі картки поточної стрічки.
    public List<NewsCard> getCards() {
        List<NewsCard> cards = new ArrayList<>();
        for (WebElement link : Dom.displayedAll(context(), this.cards)) {
            cards.add(new NewsCard(driver, link));
        }
        return cards;
    }

    // Знаходить одну картку за точним заголовком.
    public NewsCard getCard(String title) {
        for (NewsCard card : getCards()) {
            if (title.equals(card.getTitle())) {
                return card;
            }
        }
        throw new org.openqa.selenium.NoSuchElementException("News card not found: " + title);
    }

    // Скролимо стрічку донизу.
    public void loadMore() {
        WebElement marker = Dom.first(context(), infiniteScroll);
        if (marker != null) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", marker);
            return;
        }
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    private org.openqa.selenium.SearchContext context() {
        return Dom.context(driver, parent);
    }
}
