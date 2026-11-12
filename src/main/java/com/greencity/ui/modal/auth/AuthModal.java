package com.greencity.ui.modal.auth;

import com.greencity.ui.modal.BaseModal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthModal extends BaseModal {

    @FindBy(css = "#email")
    private WebElement emailInput;

    @FindBy(css = "#firstName")
    private WebElement nameInput;

    @FindBy(css = "#password")
    private WebElement passwordInput;

    @FindBy(css = "#repeatPassword")
    private WebElement confirmPasswordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    @FindBy(css = "a[aria-label='sign in modal window']")
    private WebElement signInLink;

    @FindBy(css = "a[aria-label='sign up modal window']")
    private WebElement signUpLink;

    public AuthModal(WebDriver driver) {
        this(driver, new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("app-auth-modal"))));
    }

    public AuthModal(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public AuthModal waitUntilSignInOpen() {
        wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(
                rootElement, By.cssSelector("app-sign-in #email")));
        return this;
    }

    public AuthModal waitUntilSignUpOpen() {
        wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(
                rootElement, By.cssSelector("app-sign-up #firstName")));
        return this;
    }

    public AuthModal openSignIn() {
        waitUntilElementClickable(signInLink);
        signInLink.click();
        return waitUntilSignInOpen();
    }

    public AuthModal openSignUp() {
        waitUntilElementClickable(signUpLink);
        signUpLink.click();
        return waitUntilSignUpOpen();
    }

    public AuthModal enterEmail(String email) {
        fill(emailInput, email);
        return this;
    }

    public AuthModal enterName(String name) {
        fill(nameInput, name);
        return this;
    }

    public AuthModal enterPassword(String password) {
        fill(passwordInput, password);
        return this;
    }

    public AuthModal enterConfirmPassword(String password) {
        fill(confirmPasswordInput, password);
        return this;
    }

    public boolean isSubmitEnabled() {
        waitUntilElementVisible(submitButton);
        return submitButton.isEnabled();
    }

    public void submit() {
        waitUntilElementClickable(submitButton);
        submitButton.click();
    }

    public void signIn(String email, String password) {
        waitUntilSignInOpen();
        enterEmail(email);
        enterPassword(password);
        submit();
    }

    public void signUp(String name, String email, String password) {
        signUp(name, email, password, password);
    }

    public void signUp(String name, String email, String password, String confirmation) {
        waitUntilSignUpOpen();
        enterEmail(email);
        enterName(name);
        enterPassword(password);
        enterConfirmPassword(confirmation);
        submit();
    }

    public String getRegistrationMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("mat-snack-bar-container"))).getText().trim();
    }

    public String getError() {
        By errorLocator = By.cssSelector(
                ".alert-general-error, .error-message, .validation-email-error, .validation-password-error");

        return wait.until(driver -> {
            for (WebElement error : rootElement.findElements(errorLocator)) {
                if (error.isDisplayed()) {
                    String message = error.getText().trim();

                    if (!message.isEmpty()) {
                        return message;
                    }
                }
            }
            return null;
        });
    }

    private void fill(WebElement input, String value) {
        waitUntilElementClickable(input);
        input.clear();
        input.sendKeys(value);
    }
}
