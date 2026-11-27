package com.greencity.ui.testrunners;

import com.greencity.ui.container.home.HomeHero;
import com.greencity.ui.modal.auth.AuthModal;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;


@DisplayName("GreenCity Home: hero block")
public class GreenCityHomeHeroTest extends BaseTestRunner {

    private static final String EXPECTED_H1 = "A new way to cultivate useful habits";
    private static final String START_BTN_TEXT = "Start forming a habit!";
    private static final String SIGN_IN_TITLE = "Welcome back!";

    @Test
    @Tag("smoke")
    @Tag("P0")
    @DisplayName("TC-P0-HOME-01. Hero and Start forming a habit")
    void heroBlockAndStartFormingHabitOpenSignIn() {
        SoftAssertions softly = new SoftAssertions();

        //Preconditions: Role: Guest, language: En
        homePage.getHeader().switchToEnglish();
        HomeHero hero = homePage.getHero();

        //Step 1 - Check the hero block
        softly.assertThat(hero.getTitle())
                .as("H1 text")
                .isEqualTo(EXPECTED_H1);
        softly.assertThat(hero.getDescription())
                .as("Hero description text")
                .isNotBlank();
        softly.assertThat(hero.getDescription())
                .as("Hero description text")
                .isNotBlank();
        softly.assertThat(hero.isIllustrationDisplayed())
                .as("Hero illustration is visible")
                .isTrue();
        softly.assertThat(hero.getStartButtonText())
                .as("Start button text")
                .isEqualTo(START_BTN_TEXT);

        //Step 2 - Click "Start forming a habit!"
        AuthModal signIn = hero.startHabitAsGuest().waitUntilSignInOpen();

        softly.assertThat(signIn.isOpen())
                .as("Sign in modal is visible")
                .isTrue();
        softly.assertThat(signIn.getTitle())
                .as("Sign in modal title")
                .isEqualTo(SIGN_IN_TITLE);

        softly.assertAll();
    }
}