package com.greencity.ui.container.saved;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.component.OptionalFind;
import com.greencity.ui.page.news.NewsListPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SavedTabs extends BaseComponent {
    private final By root = By.cssSelector("app-saved-section");
    private final By title = By.cssSelector("app-saved-section p.main-header");
    private final By newsTab = By.cssSelector("app-saved-section button.tab");

    public SavedTabs(WebDriver driver) {
        super(driver, driver.findElement(By.tagName("body")));
    }

    @Override
    public boolean isDisplayed() {
        return OptionalFind.displayed(driver, root) != null;
    }

    public String getTitle() {
        return OptionalFind.text(driver, title);
    }

    public NewsListPage openNews() {
        OptionalFind.click(driver, newsTab);
        return new NewsListPage(driver);
    }

    public boolean isNewsTabActive() {
        return OptionalFind.hasClass(OptionalFind.displayed(driver, newsTab), "active-tab");
    }
}
