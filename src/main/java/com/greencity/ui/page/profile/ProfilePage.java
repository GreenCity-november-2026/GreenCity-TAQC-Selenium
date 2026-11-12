package com.greencity.ui.page.profile;

import com.greencity.ui.page.GreenCityPage;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends GreenCityPage {

    @Override
    protected String getRoute() {
        return ".*#/greenCity/profile/\\d+/?$";
    }

    public ProfilePage(WebDriver driver) {
        super(driver);
    }
}