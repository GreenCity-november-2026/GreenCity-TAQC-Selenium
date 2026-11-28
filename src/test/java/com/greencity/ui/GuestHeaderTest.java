package com.greencity.ui;

import com.greencity.ui.component.header.HeaderComponent;
import com.greencity.ui.modal.auth.AuthModal;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GuestHeaderTest extends BaseTestRunner {

    @Test
    @DisplayName("TC-P0-HDR-01. Guest header")
    void guestHeaderShowsControlsAndRemainsVisibleAfterScrolling() {
        HeaderComponent header = homePage.getHeader();
        header.selectLanguage("En");

        assertAll("Guest header",
                () -> assertTrue(header.isLogoVisible(), "GreenCity logo is not visible"),
                () -> assertTrue(header.isSignInVisible(), "Sign In is not visible"),
                () -> assertTrue(header.isSignUpVisible(), "Sign Up is not visible"),
                () -> assertTrue(header.isLanguageSwitcherVisible(), "Language switcher is not visible")
        );

        assertEquals(List.of("En", "Uk"), header.getAvailableLanguages());

        AuthModal signIn = header.openSignIn();
        assertEquals("Welcome back!", signIn.getTitle());
        signIn.close();

        AuthModal signUp = header.openSignUp();
        assertEquals("Hello!", signUp.getTitle());
        signUp.close();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo({top: document.body.scrollHeight, behavior: 'instant'})");

        assertTrue(((Number) js.executeScript("return window.scrollY")).longValue() > 0,
                "The page should scroll down");
        assertTrue(header.isLogoInViewport(),
                "The header logo should remain visible after scrolling down");

        js.executeScript("window.scrollTo({top: 0, behavior: 'instant'})");

        assertEquals(0L, ((Number) js.executeScript("return window.scrollY")).longValue());
        assertTrue(header.isLogoInViewport(),
                "The header logo should be visible at the top");
    }
}