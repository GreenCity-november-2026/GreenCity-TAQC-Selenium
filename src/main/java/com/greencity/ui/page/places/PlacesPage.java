package com.greencity.ui.page.places;

import com.greencity.ui.page.GreenCityPage;
import org.openqa.selenium.WebDriver;

public class PlacesPage extends GreenCityPage {

    @Override
    protected String getRoute() {
        return ".*#/greenCity/places/?$";
    }

    public PlacesPage(WebDriver driver) {
        super(driver);
    }
}