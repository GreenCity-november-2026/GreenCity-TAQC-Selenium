package com.greencity.ui.page.events;

import com.greencity.ui.page.GreenCityPage;
import org.openqa.selenium.WebDriver;

public class EventsListPage extends GreenCityPage {

    @Override
    protected String getRoute() {
        return ".*/greenCity/events/?$";
    }

    public EventsListPage(WebDriver driver) {
        super(driver);
    }
}