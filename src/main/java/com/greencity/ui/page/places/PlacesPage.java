package com.greencity.ui.page.places;

import com.greencity.ui.page.GreenCityPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PlacesPage extends GreenCityPage {
    @FindBy(css = "app-places button.secondary-global-button")
    private WebElement addPlaceButton;

    @FindBy(css = "app-places input.search-write")
    private WebElement search;

    @Override
    protected String getRoute() {
        return ".*#/greenCity/places/?$";
    }

    public PlacesPage(WebDriver driver) {
        super(driver);
    }

    public String getAddPlaceButtonText() {
        return wait.until(ExpectedConditions.visibilityOf(addPlaceButton)).getText().trim();
    }

    public String getSearchPlaceholder() {
        return wait.until(ExpectedConditions.visibilityOf(search)).getDomAttribute("placeholder");
    }
}