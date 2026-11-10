package com.greencity.ui.container.home;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.page.profile.ProfilePage;
import com.greencity.ui.modal.auth.AuthModal;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomeHero extends BaseComponent {

    @FindBy(css = "#header-left h1")
    private WebElement title;

    @FindBy(css = "#header-left h1")
    private WebElement text;

    @FindBy(css = "#header-left button")
    private WebElement startFormingHabitButton;

    @FindBy(css = "#header-left img")
    private WebElement heroImg;

    public HomeHero(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public String getTitle() {
        waitUntilElementVisible(title);
        return title.getText().trim();
    }

    public String getDescription() {
        waitUntilElementVisible(text);
        return text.getText().trim();
    }

    //For registered User
    public ProfilePage startHabit() {
        waitUntilElementClickable(startFormingHabitButton);
        startFormingHabitButton.click();
        return new ProfilePage(driver);
    }

    //For User as a Guest
    public AuthModal startHabitAsGuest() {
        waitUntilElementClickable(startFormingHabitButton);
        startFormingHabitButton.click();
        return new AuthModal(driver, rootElement);
    }
}
