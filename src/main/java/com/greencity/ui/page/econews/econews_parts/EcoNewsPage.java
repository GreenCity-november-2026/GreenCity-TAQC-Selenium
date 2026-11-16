package com.greencity.ui.page.econews.econews_parts;

import com.greencity.ui.page.econews.EcoNews;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

// Сторінка списку еко-новин: #/greenCity/news.
public class EcoNewsPage extends EcoNews {
    public static final String HASH = "#/greenCity/news";

    // Заголовок сторінки «Еко новини».
    public final By pageTitle = By.cssSelector("h1.main-header");

    public final NewsFilterBar filterBar;
    public final SavedSectionComponent savedSection;
    public final RemainingCountComponent remainingCount;
    public final ChangeViewButtonComponent changeView;
    public final NewsListComponent newsList;
    public final SpinnerComponent spinner;
    public final NoDataComponent noData;

    public EcoNewsPage(WebDriver driver) {
        this(driver, null);
    }

    public EcoNewsPage(WebDriver driver, WebElement parent) {
        super(driver, parent);
        this.filterBar = new NewsFilterBar(driver, parent);
        this.savedSection = new SavedSectionComponent(driver, parent);
        this.remainingCount = new RemainingCountComponent(driver, parent);
        this.changeView = new ChangeViewButtonComponent(driver, parent);
        this.newsList = new NewsListComponent(driver, parent);
        this.spinner = new SpinnerComponent(driver, parent);
        this.noData = new NoDataComponent(driver, parent);
    }

    //  Відкриває список еко-новин.
    public EcoNewsPage open() {
        String url = driver.getCurrentUrl();
        int hash = url.indexOf('#');
        String base = hash >= 0 ? url.substring(0, hash) : url;
        driver.get(base + HASH);
        return this;
    }

    // Читає заголовок сторінки.
    public String getPageTitle() {
        return Dom.text(context(), pageTitle);
    }

    // Повертає видимі картки стрічки.
    public List<NewsCard> getCards() {
        return newsList.getCards();
    }

    // Знаходить картку за заголовком.
    public NewsCard getCard(String title) {
        return newsList.getCard(title);
    }

    // Читає число новин, яке показує лічильник над стрічкою.
    public int getItemsCount() {
        return remainingCount.getCount();
    }

    // Повідомляє, чи стрічка в режимі збережених новин.
    public boolean isSavedMode() {
        return savedSection.isDisplayed();
    }

    // Повідомляє, чи стрічка порожня.
    public boolean isEmpty() {
        return noData.isDisplayed();
    }

    // Прокручує стрічку, щоб підвантажити наступні новини.
    public void loadMore() {
        newsList.loadMore();
    }
}
