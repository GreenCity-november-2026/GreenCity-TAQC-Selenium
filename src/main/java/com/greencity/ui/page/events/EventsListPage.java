package com.greencity.ui.page.events;

import com.greencity.ui.page.GreenCityPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class EventsListPage extends GreenCityPage {
    @FindBy(css = "app-events-list p.main-header")
    private WebElement pageTitle;

    @Override
    protected String getRoute() {
        return ".*/greenCity/events/?$";
    }

    public EventsListPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return wait.until(ExpectedConditions.visibilityOf(pageTitle)).getText().trim();
    }
}