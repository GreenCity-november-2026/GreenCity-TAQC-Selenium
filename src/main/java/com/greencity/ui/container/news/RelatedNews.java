package com.greencity.ui.container.news;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.component.NewsCard;
import com.greencity.ui.component.OptionalFind;
import com.greencity.ui.page.news.NewsDetailPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class RelatedNews extends BaseComponent {
    private final By title = By.cssSelector("app-eco-news-widget p");
    private final By cards = By.cssSelector("app-eco-news-widget app-news-list-gallery-view");

    public RelatedNews(WebDriver driver) {
        super(driver, driver.findElement(By.tagName("body")));
    }

    public String getTitle() {
        return OptionalFind.text(driver, title);
    }

    public List<NewsCard> getCards() {
        List<NewsCard> values = new ArrayList<>();
        for (WebElement card : OptionalFind.displayedAll(driver, cards)) {
            values.add(new NewsCard(driver, card));
        }
        return values;
    }

    public NewsDetailPage open(String title) {
        for (NewsCard card : getCards()) {
            if (title.equals(card.getTitle())) {
                return card.open();
            }
        }
        throw new NoSuchElementException("Related news not found: " + title);
    }
}
