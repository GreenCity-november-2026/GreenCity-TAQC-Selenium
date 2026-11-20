package com.greencity.ui.testrunners;

import com.greencity.ui.component.footer.FooterComponent;
import com.greencity.ui.modal.auth.AuthModal;
import com.greencity.ui.page.GreenCityPage;
import com.greencity.ui.page.homepage.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class FooterSmokeTest extends BaseTestRunner {
    private static final String HOME_ROUTE = ".*#/greenCity/?$";
    private static final String NEWS_HASH = "#/greenCity/news";
    private static final String EVENTS_HASH = "#/greenCity/events";
    private static final String PLACES_HASH = "#/greenCity/places";
    private static final String ABOUT_HASH = "#/greenCity/about";

    private static final List<String> EXPECTED_LINKS = List.of(
            "Eco news", "Events", "Places", "About Us", "My Space", "UBS Courier");

    @Test
    @DisplayName("P0-FTR-01. Verify Footer section links and logo as a guest")
    void footerWorksForGuest() {
        FooterComponent footer = homePage.getFooter().scrollIntoView();

        //Step 1:  Check that the footer, Follow us, and the copyright are visible
        Map<String, String> links = footer.getNavLinks();
        String copyright = footer.getCopyright();
        String year = String.valueOf(Year.now().getValue());

        assertAll("Footer content",
                () -> assertTrue(footer.isDisplayed(), "Footer is not visible"),
                () -> assertEquals("Follow us", footer.getFollowUsText()),
                () -> assertEquals(EXPECTED_LINKS, List.copyOf(links.keySet())),
                () -> assertEquals(NEWS_HASH, links.get("Eco news")),
                () -> assertEquals(EVENTS_HASH, links.get("Events")),
                () -> assertEquals(PLACES_HASH, links.get("Places")),
                () -> assertEquals(ABOUT_HASH, links.get("About Us")),
                () -> assertTrue(copyright.contains("Green City"), "Copyright:" + copyright),
                () -> assertTrue(copyright.contains(year), "Copyright: " + copyright));

        //Step 2-4:
        // 2. Click footer Eco news.
        // 3. Return home with the footer logo.
        // 4. Repeat for Events, Places, and About Us
        HomePage home = openSectionAndReturnHome(homePage, FooterComponent::openEcoNews, NEWS_HASH);
        home = openSectionAndReturnHome(home, FooterComponent::openEvents, EVENTS_HASH);
        home = openSectionAndReturnHome(home, FooterComponent::openPlaces, PLACES_HASH);
        home = openSectionAndReturnHome(home, FooterComponent::openAbout, ABOUT_HASH);

    // Step 5: My Space opens Sign in
    AuthModal authModal = home.getFooter().openMySpace().waitUntilSignInOpen();

    assertAll("My Space for a guest",
                      () -> assertTrue(authModal.isOpen(), "Auth modal is not open"),
            () -> assertEquals("Welcome back!", authModal.getTitle()));
    }

    private HomePage openSectionAndReturnHome(HomePage home,
                                              Function<FooterComponent, ? extends GreenCityPage> openSection,
                                              String hash) {
        GreenCityPage section = openSection.apply(home.getFooter().scrollIntoView());
        assertTrue(section.isLoaded(), "Section page is not loaded: " + hash);
        assertTrue(driver.getCurrentUrl().endsWith(hash),
                "Expected URL to end with " + hash + " but was " + driver.getCurrentUrl());

        HomePage back = section.getFooter().scrollIntoView().openHome();
        assertTrue(back.isLoaded(), "Home page is not loaded after the footer logo");
        assertTrue(driver.getCurrentUrl().matches(HOME_ROUTE),
                "Footer logo opened " + driver.getCurrentUrl());
        return back;
    }
}