package com.greencity.ui.component.home;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.modal.auth.AuthModal;
import com.greencity.ui.page.profile.ProfilePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StatRow extends BaseComponent {
    @FindBy(css = ".stat-row-content h3")
    private WebElement title;

    @FindBy(css = ".stat-row-content h3 span")
    private WebElement counter;

    @FindBy(css = ".stat-row-content p")
    private WebElement question;

    @FindBy(css = ".stat-row-content button")
    private WebElement startHabitButton;
    private static final String UBS_HASH = "#/ubs";

    @FindBy(css = ".stat-row-content .location-row a")
    private WebElement placeLink;

    public StatRow(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public String getTitle() {
        return title.getText().trim();
    }

    public String getCounterText() {
        return counter.getText().trim();
    }

    public int getCounter() {
        return Integer.parseInt(getCounterText().replaceAll("\\D", ""));
    }

    public String getQuestion() {
        return question.getText().trim();
    }

    @Step("Start habit from stat row")
    public ProfilePage startHabit() {
        clickStartHabit();
        return new ProfilePage(driver);
    }

    @Step("Start habit from stat row as guest")
    public AuthModal startHabitAsGuest() {
        clickStartHabit();
        return new AuthModal(driver);
    }

    private void clickStartHabit() {
        scrollToElementJs(startHabitButton);
        startHabitButton.click();
    }

    public String getPlaceLinkText() {
        return placeLink.getText().trim();
    }

    @Step("Open place link from stat row")
    public void openPlaceLink() {
        scrollToElementJs(placeLink);
        placeLink.click();
        wait.until(ExpectedConditions.urlContains(UBS_HASH));
    }
}
