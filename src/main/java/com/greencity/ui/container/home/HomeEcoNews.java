package com.greencity.ui.container.home;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.component.home.HomeNewsCard;
import com.greencity.ui.page.news.NewsDetailPage;
import com.greencity.ui.page.news.NewsListPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomeEcoNews extends BaseComponent {

    /**
     * News are loaded asynchronously; this is how long we wait before treating the block as empty.
     */
    private static final Duration NEWS_LOAD_TIMEOUT = Duration.ofSeconds(5);
    private static final By NEWS_WRAPPER = By.cssSelector(".eco-events-wrapper");

    @FindBy(css = "app-eco-events-item")
    private List<WebElement> cardRoots;

    @FindBy(css = "app-eco-events a.centered")
    private WebElement readAllNewsLink;

    public HomeEcoNews(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @Step("Get eco news cards")
    public List<HomeNewsCard> getCards() {
        if (isEmpty()) {
            return List.of();
        }
        return cardRoots.stream()
                .map(root -> new HomeNewsCard(driver, root))
                .filter(card -> !card.getTitle().isBlank())
                .toList();
    }

    @Step("Open eco news card '{title}'")
    public NewsDetailPage openCard(String title) {
        return getCards().stream()
                .filter(card -> card.getTitle().equals(title))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No eco news card with title: " + title))
                .open();
    }

    @Step("Click 'Read all news'")
    public NewsListPage readAllNews() {
        scrollToElementJs(readAllNewsLink);
        readAllNewsLink.click();
        return new NewsListPage(driver);
    }

    @Step("Check whether eco news block is empty")
    public boolean isEmpty() {
        try {
            new WebDriverWait(driver, NEWS_LOAD_TIMEOUT)
                    .until(d -> !rootElement.findElements(NEWS_WRAPPER).isEmpty());
            return false;
        } catch (TimeoutException e) {
            return true;
        }
    }
}