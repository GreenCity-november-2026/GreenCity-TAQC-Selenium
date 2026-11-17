package com.greencity.ui.container.news;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.component.OptionalFind;
import com.greencity.ui.component.TagFilter;
import com.greencity.ui.modal.auth.AuthModal;
import com.greencity.ui.page.news.CreateEditNewsPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NewsFilterBar extends BaseComponent {
    private static final String CREATE_HASH = "#/greenCity/news/create-news";

    private final By searchToggle = By.cssSelector("span.search-img");
    private final By searchInput = By.cssSelector("input.place-input");
    private final By clearSearchButton = By.cssSelector("img.cross-position[alt='cancel search']");
    private final By bookmarkButton = By.cssSelector("span.bookmark-img");
    private final By createButton = By.cssSelector("#create-button");

    @Getter
    private final TagFilter tags;

    public NewsFilterBar(WebDriver driver) {
        super(driver, driver.findElement(By.tagName("body")));
        this.tags = new TagFilter(driver);
    }

    public void search(String text) {
        if (OptionalFind.displayed(driver, searchInput) == null) {
            OptionalFind.click(driver, searchToggle);
        }
        WebElement input = OptionalFind.displayed(driver, searchInput);
        input.clear();
        input.sendKeys(text);
    }

    public void clearSearch() {
        OptionalFind.click(driver, clearSearchButton);
    }

    public String getSearchText() {
        WebElement input = OptionalFind.displayed(driver, searchInput);
        if (input == null) {
            return "";
        }
        String value = input.getAttribute("value");
        return value == null ? "" : value;
    }

    public void toggleBookmark() {
        OptionalFind.click(driver, bookmarkButton);
    }

    public boolean isBookmarkActive() {
        return OptionalFind.hasClass(OptionalFind.displayed(driver, bookmarkButton), "bookmark-img-active");
    }

    public boolean isCreateAvailable() {
        return OptionalFind.displayed(driver, createButton) != null;
    }

    public CreateEditNewsPage openCreate() {
        OptionalFind.click(driver, createButton);
        return new CreateEditNewsPage(driver);
    }

    public AuthModal openCreateAsGuest() {
        WebElement button = OptionalFind.displayed(driver, createButton);
        if (button != null) {
            button.click();
        } else {
            String url = driver.getCurrentUrl();
            int hash = url.indexOf('#');
            String base = hash >= 0 ? url.substring(0, hash) : url;
            driver.get(base + CREATE_HASH);
        }
        return new AuthModal(driver);
    }
}
