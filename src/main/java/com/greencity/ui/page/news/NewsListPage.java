package com.greencity.ui.page.news;

import com.greencity.ui.component.NewsCard;
import com.greencity.ui.component.NoDataBlock;
import com.greencity.ui.component.OptionalFind;
import com.greencity.ui.container.news.NewsFilterBar;
import com.greencity.ui.container.saved.SavedTabs;
import com.greencity.ui.page.GreenCityPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class NewsListPage extends GreenCityPage {
    private static final String HASH = "#/greenCity/news";
    private static final By CARDS = By.cssSelector("ul.list a.link");
    private static final By EMPTY = By.cssSelector("app-no-data");
    private static final By PAGE_TITLE = By.cssSelector("h1.main-header");
    private static final By GALLERY_BUTTON = By.cssSelector("app-change-view-button span.btn-tiles");
    private static final By LIST_BUTTON = By.cssSelector("app-change-view-button span.btn-bars");
    private static final By REMAINING_COUNT = By.cssSelector("app-remaining-count h2");
    private static final By INFINITE_SCROLL = By.cssSelector("[infinitescroll]");

    @Getter
    private final NewsFilterBar filterBar;
    @Getter
    private final SavedTabs savedTabs;
    @Getter
    private final NoDataBlock noData;

    public NewsListPage(WebDriver driver) {
        super(driver);
        this.filterBar = new NewsFilterBar(driver);
        this.savedTabs = new SavedTabs(driver);
        this.noData = new NoDataBlock(driver);
    }

    @Override
    protected String getRoute() {
        return ".*#/greenCity/news/?$";
    }

    @Override
    public boolean isLoaded() {
        boolean loaded = super.isLoaded();
        waitUntilFeedSettled();
        return loaded;
    }

    public NewsListPage open() {
        String url = driver.getCurrentUrl();
        int hash = url.indexOf('#');
        String base = hash >= 0 ? url.substring(0, hash) : url;
        driver.get(base + HASH);
        return this;
    }

    public String getPageTitle() {
        return OptionalFind.text(driver, PAGE_TITLE);
    }

    public List<NewsCard> getCards() {
        waitUntilFeedSettled();
        if (noData.isDisplayed()) {
            return List.of();
        }
        List<NewsCard> cards = new ArrayList<>();
        for (WebElement link : OptionalFind.displayedAll(driver, CARDS)) {
            cards.add(new NewsCard(driver, link));
        }
        return cards;
    }

    public NewsCard getCard(String title) {
        for (NewsCard card : getCards()) {
            if (title.equals(card.getTitle())) {
                return card;
            }
        }
        throw new NoSuchElementException("News card not found: " + title);
    }

    public int getItemsCount() {
        return OptionalFind.firstInt(OptionalFind.text(driver, REMAINING_COUNT));
    }

    public boolean isSavedMode() {
        return savedTabs.isDisplayed();
    }

    public boolean isEmpty() {
        waitUntilFeedSettled();
        return noData.isDisplayed();
    }

    public void showGallery() {
        OptionalFind.click(driver, GALLERY_BUTTON);
    }

    public void showList() {
        OptionalFind.click(driver, LIST_BUTTON);
    }

    public boolean isGalleryView() {
        return OptionalFind.hasClass(OptionalFind.displayed(driver, GALLERY_BUTTON), "btn-tiles-active");
    }

    public void loadMore() {
        WebElement marker = OptionalFind.first(driver, INFINITE_SCROLL);
        if (marker != null) {
            threadJs.executeScript("arguments[0].scrollIntoView(true);", marker);
            return;
        }
        threadJs.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    private void waitUntilFeedSettled() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(CARDS),
                ExpectedConditions.presenceOfElementLocated(EMPTY)));
    }
}
