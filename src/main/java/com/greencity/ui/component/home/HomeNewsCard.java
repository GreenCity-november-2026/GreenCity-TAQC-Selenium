package com.greencity.ui.component.home;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.page.news.NewsDetailPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomeNewsCard extends BaseComponent {
    @FindBy(css = "#events h2.section-caption")
    private WebElement title;

    @FindBy(css = "a.read-more")
    private WebElement readMoreLink;

    public HomeNewsCard(WebDriver driver, WebElement element) {
        super(driver, element);
    }
    public String getTitle() {
        return title.getText().trim();
    }

    @Step("Open news card")
    public NewsDetailPage open() {
        scrollToElementJs(readMoreLink);
        readMoreLink.click();
        return new NewsDetailPage(driver);
    }
}
