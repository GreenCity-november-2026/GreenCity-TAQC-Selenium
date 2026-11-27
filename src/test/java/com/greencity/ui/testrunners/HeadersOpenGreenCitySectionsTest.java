package com.greencity.ui.testrunners;

import com.greencity.ui.component.header.HeaderComponent;
import com.greencity.ui.modal.auth.AuthModal;
import com.greencity.ui.page.about.AboutPage;
import com.greencity.ui.page.events.EventsListPage;
import com.greencity.ui.page.homepage.HomePage;
import com.greencity.ui.page.news.NewsListPage;
import com.greencity.ui.page.places.PlacesPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeadersOpenGreenCitySectionsTest extends BaseTestRunner {
    private static final String NEWS_HASH = "#/greenCity/news";
    private static final String EVENTS_HASH = "#/greenCity/events";
    private static final String PLACES_HASH = "#/greenCity/places";
    private static final String ABOUT_US_HASH = "#/greenCity/about";
    private static final String HOME_ROUTE = ".*#/greenCity/?$";

    private static final String ECO_NEWS_H1 = "Eco news";
    private static final String EVENTS_HEADING = "Events";
    private static final String PLACES_SEARCH = "Searching for a place";
    private static final String ADD_PLACE = "Add place";
    private static final String ABOUT_HEADING = "About Us";
    private static final String HOME_H1 = "A new way to cultivate useful habits";

    @Test
    @Tag("smoke")
    @Tag("P0")
    @DisplayName("TC-P0-NAV-01. Header opens GreenCity sections")
    void headerOpensGreenCitySections() {
        //Preconditions: Role: Guest, language: En
        HeaderComponent header = homePage.getHeader().switchToEnglish();
        assertTrue(homePage.isLoaded(), "Home page is not loaded");

        //Step 1 - Click Eco news
        NewsListPage news = header.openEcoNews();
        assertTrue(news.isLoaded(), "Eco news page is not loaded");
        assertTrue(driver.getCurrentUrl().endsWith(NEWS_HASH), "Expected url ends with " + NEWS_HASH +
                ", but was found " + driver.getCurrentUrl());
        assertEquals(ECO_NEWS_H1, news.getPageTitle(), "Eco news H1 differs");
        assertTrue(news.getHeader().isEcoNewsActive(), "Eco news header is not active");

        //Step 2 - Click Events
        EventsListPage events = news.getHeader().openEvents();
        assertTrue(events.isLoaded(), "Events page is not loaded");
        assertTrue(driver.getCurrentUrl().endsWith(EVENTS_HASH), "Expected url ends with " + EVENTS_HASH +
                ", but was found " + driver.getCurrentUrl());
        assertEquals(EVENTS_HEADING, events.getPageTitle(), "Events heading");

        //Step 3 - Click Places
        PlacesPage places = events.getHeader().openPlaces();
        assertTrue(places.isLoaded(), "Places page is not loaded");
        assertTrue(driver.getCurrentUrl().endsWith(PLACES_HASH), "Expected url ends with " + PLACES_HASH +
                ", but was found " + driver.getCurrentUrl());
        assertEquals(PLACES_SEARCH, places.getSearchPlaceholder(), "Places search placeholder");
        assertEquals(ADD_PLACE, places.getAddPlaceButtonText(), "Add place button");
        closeSignInIfShown();

        // Step 4 - Click About us
        AboutPage about = places.getHeader().openAbout();
        assertTrue(about.isLoaded(), "About page is not loaded");
        assertTrue(driver.getCurrentUrl().endsWith(ABOUT_US_HASH),
                "Expected URL to end with " + ABOUT_US_HASH + " but was " + driver.getCurrentUrl());
        assertEquals(ABOUT_HEADING, about.getHeaderText(), "About heading");

        // Step 5 - Click the GREENCITY logo
        HomePage home = places.getHeader().openHome();
        assertTrue(home.isLoaded(), "Home page is not loaded after the logo");
        assertTrue(driver.getCurrentUrl().matches(HOME_ROUTE),
                "Logo opened " + driver.getCurrentUrl());
        assertEquals(HOME_H1, home.getHero().getTitle(), "Home H1");
    }

    private void closeSignInIfShown() {
        try {
            new AuthModal(driver).waitUntilSignInOpen().close();
        } catch (TimeoutException ignored) {
            // the modal did not appear
        }
    }
}