package com.greencity.ui.page.about;

import com.greencity.ui.page.GreenCityPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AboutPage extends GreenCityPage {

    @FindBy(css = "app-about-page h2.section__header")
    private WebElement pageHeader;

    @Override
    protected String getRoute() {
        return ".*#/greenCity/about/?$";
    }

    public AboutPage(WebDriver driver) {
        super(driver);
    }

    public String getHeaderText() {
        return wait.until(ExpectedConditions.visibilityOf(pageHeader)).getText().trim();
    }
}