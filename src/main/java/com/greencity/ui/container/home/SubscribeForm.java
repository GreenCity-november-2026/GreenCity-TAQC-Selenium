package com.greencity.ui.container.home;

import com.greencity.ui.component.BaseComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SubscribeForm extends BaseComponent {
    private static final By SNACK_BAR = By.cssSelector("mat-snack-bar-container");

    @FindBy(css = ".form-input input[type='email']")
    private WebElement emailInput;

    @FindBy(css = ".form-input button")
    private WebElement subscribeButton;

    @FindBy(css = "#validation-error")
    private WebElement validationError;

    public SubscribeForm(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @Step("Enter email '{email}' into subscribe form")
    public SubscribeForm enterEmail(String email) {
        scrollToElementJs(emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    @Step("Subscribe with email '{email}'")
    public void subscribe(String email) {
        enterEmail(email);
        waitUntilElementClickable(subscribeButton);
        subscribeButton.click();
    }

    @Step("Get subscribe result message")
    public String getMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SNACK_BAR)).getText().trim();
    }

    public String getValidationError() {
        return validationError.getText().trim();
    }

    // The error is always in the DOM; Angular toggles the "hidden" / "visible" classes.
    public boolean isValidationErrorDisplayed() {
        String cssClass = validationError.getAttribute("class");
        return cssClass != null && cssClass.contains("visible") && validationError.isDisplayed();
    }
}