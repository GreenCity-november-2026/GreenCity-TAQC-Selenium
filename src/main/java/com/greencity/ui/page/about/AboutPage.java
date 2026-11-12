package com.greencity.ui.page.about;

import com.greencity.ui.page.GreenCityPage;
import org.openqa.selenium.WebDriver;

public class AboutPage extends GreenCityPage {

    @Override
    protected String getRoute() {
        return ".*#/greenCity/about/?$";
    }

    public AboutPage(WebDriver driver) {
        super(driver);
    }
}