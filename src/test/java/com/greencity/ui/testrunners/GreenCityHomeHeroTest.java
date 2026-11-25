package com.greencity.ui.testrunners;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@DisplayName("GreenCity Home: hero block")
public class GreenCityHomeHeroTest extends BaseTestRunner {

    private static final String GREENCITY_URL = System.getProperty(
            "greencity.url", "https://www.greencity.cx.ua/#/greenCity");
    private static final String EXPECTED_H1 = "A new way to cultivate useful habits";
    private static final String START_BTN_TEXT = "Start forming a habit!";
    private static final String SIGN_IN_TITLE = "Welcome back!";

    private static final By LANG_SWITCHER = By.xpath(
            "//li[contains(@class, 'lang-option')][normalize-space()='Ua' or normalize-space()='En']");
    private static final By LANG_EN_OPTION = By.xpath("//*[contains(@class, 'lang-option')]//" +
            "button[normalize-space()='En']");
    private static final By HERO_H1 = By.xpath("//h1[normalize-space()='" + EXPECTED_H1 + "']");
    private static final By HERO_DESCRIPTION = By.xpath("//h1[normalize-space()='" + EXPECTED_H1 +
            "']/following-sibling::p[normalize-space()]");
    private static final By HERO_ILLUSTRATION = By.xpath("//img[@id='guy-image']");
    private static final By START_BTN = By.xpath("//button[normalize-space()='" + START_BTN_TEXT + "']");

    private static final By SIGN_IN_MODAL = By.xpath("//app-auth-modal");
    private static final By SIGN_IN_HEADER = By.xpath("//app-auth-modal//h1[normalize-space()='"
            + SIGN_IN_TITLE + "']");

    private static WebDriverWait wait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    @Tag("smoke")
    @Tag("P0")
    @DisplayName("TC-P0-HOME-01. Hero and Start forming a habit")
    void heroBlockAndStartFormingHabitOpenSignIn() {
        SoftAssertions softly = new SoftAssertions();

        //Preconditions
        openAsGuest(driver);
        switchToEnglish(driver);

        //Step 1 - Check the hero block
        WebElement h1 = waitVisibleOrNull(driver, HERO_H1);
        WebElement description = waitVisibleOrNull(driver, HERO_DESCRIPTION);
        WebElement startBtn = waitVisibleOrNull(driver, START_BTN);

        softly.assertThat(textOf(h1))
                .as("H1 text")
                .isEqualTo(EXPECTED_H1);
        softly.assertThat(description)
                .as("Hero description is visible")
                .isNotNull();
        softly.assertThat(textOf(description))
                .as("Hero description text")
                .isNotBlank();
        softly.assertThat(isDisplayed(driver, HERO_ILLUSTRATION))
                .as("Hero illustration is visible")
                .isTrue();
        softly.assertThat(startBtn)
                .as("'Start forming a habit!' button is visible")
                .isNotNull();
        softly.assertThat(textOf(startBtn))
                .as("Start button text")
                .isEqualTo(START_BTN_TEXT);

        //Spet 2 - Click "Start forming a habit!"
        if (startBtn != null) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", startBtn);
            wait(driver).until(ExpectedConditions.elementToBeClickable(startBtn)).click();

            WebElement modal = waitVisibleOrNull(driver, SIGN_IN_MODAL);
            WebElement title = waitVisibleOrNull(driver, SIGN_IN_HEADER);

            softly.assertThat(modal)
                    .as("Sign in modal is visible")
                    .isNotNull();
            softly.assertThat(textOf(title))
                    .as("Sign in modal title")
                    .isEqualTo(SIGN_IN_TITLE);
        }

        softly.assertAll();
    }

    private static void openAsGuest(WebDriver driver) {
        driver.get(GREENCITY_URL);
        driver.manage().deleteAllCookies();
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear();" +
                "window.sessionStorage.clear();");
        driver.navigate().refresh();
        wait(driver).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));
    }

    private static void switchToEnglish(WebDriver driver) {
        if (!driver.findElements(HERO_H1).isEmpty()) {
            return;
        }
        wait(driver).until(ExpectedConditions.elementToBeClickable(LANG_SWITCHER)).click();
        wait(driver).until(ExpectedConditions.elementToBeClickable(LANG_EN_OPTION)).click();
        wait(driver).until(ExpectedConditions.visibilityOfElementLocated(HERO_H1));
    }

    private static WebElement waitVisibleOrNull(WebDriver driver, By locator) {
        try {
            return wait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return null; // return null as do not to break soft-assertion
        }
    }

    private static boolean isDisplayed(WebDriver driver, By locator) {
        List<WebElement> found = driver.findElements(locator);
        return !found.isEmpty() && found.get(0).isDisplayed();
    }

    private static String textOf(WebElement element) {
        return element == null ? null : element.getText().trim();
    }
}