package com.greencity.ui;

import com.greencity.ui.component.header.HeaderComponent;
import com.greencity.ui.modal.auth.AuthModal;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.net.URI;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GuestMySpaceAndUbsTest extends BaseTestRunner {

    @Test
    @DisplayName("TC-P0-NAV-02. My space and UBS courier")
    void mySpaceOpensSignInAndUbsCourierOpensUbs() {
        HeaderComponent header = homePage.getHeader();
        header.selectLanguage("En");

        String homeUrl = driver.getCurrentUrl();
        AuthModal signIn = header.openMySpace();

        assertAll("My space for a guest",
                () -> assertTrue(signIn.isOpen(), "Sign in modal should be visible"),
                () -> assertEquals("Welcome back!", signIn.getTitle()),
                () -> assertEquals(homeUrl, driver.getCurrentUrl(),
                        "The guest should stay on the home page")
        );

        signIn.close();
        header.openUbs();

        assertEquals("/ubs", URI.create(driver.getCurrentUrl()).getFragment(),
                "UBS courier should open #/ubs");
    }
}