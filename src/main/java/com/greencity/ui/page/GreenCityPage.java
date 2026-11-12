package com.greencity.ui.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class GreenCityPage extends BasePage {
    protected static final String GREEN_CITY_ROUTE = "#/greenCity";

    public GreenCityPage(WebDriver driver) {
        super(driver);
    }

    protected abstract String getRoute();

    public boolean isLoaded() {
        wait.until(ExpectedConditions.urlMatches(getRoute()));
        waitUntilElementVisible(footer.getRootElement());
        return header.isDisplayed() && footer.isDisplayed();
    }
}
