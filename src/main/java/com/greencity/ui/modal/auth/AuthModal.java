package com.greencity.ui.modal.auth;

import com.greencity.ui.modal.BaseModal;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AuthModal extends BaseModal {

    private static final By ROOT = By.cssSelector("app-auth-modal");
    private static final By ERROR = By.cssSelector(
            ".alert-general-error, .error-message, .validation-email-error, .validation-password-error");
    private static final By SNACK_BAR = By.cssSelector("mat-snack-bar-container");

    @FindBy(css = ".close-modal-window")
    private WebElement closeButton;

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

    @FindBy(css = "app-sign-in h1")
    private WebElement modalTitle;

    public AuthModal(WebDriver driver) {
        super(driver, ROOT);
    }

    @Override
    @Step("Close auth modal")
    public void close() {
        waitUntilElementClickable(closeButton);
        closeButton.click();
        waitUntilElementInvisible(rootElement);
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
        try {
            return submitButton.isDisplayed() && submitButton.isEnabled();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
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
        waitForSubmitResult();
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
        waitForSubmitResult();
    }

    public String getTitle() {
        By heading = By.cssSelector("app-sign-in h1, app-sign-up h1");
        return wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(
                rootElement, heading
        )).getFirst().getText().trim();
    }

    public String getRegistrationMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SNACK_BAR)).getText().trim();
    }

    public String getError() {
        try {
            for (WebElement error : rootElement.findElements(ERROR)) {
                if (!error.isDisplayed()) {
                    continue;
                }
                String message = error.getText().trim();
                if (!message.isEmpty()) {
                    return message;
                }
            }
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return "";
        }
        return "";
    }

    private void waitForSubmitResult() {
        wait.until(driver -> !isOpen() || !getError().isEmpty() || isSnackbarVisible());
    }

    private boolean isSnackbarVisible() {
        for (WebElement snackbar : driver.findElements(SNACK_BAR)) {
            try {
                if (snackbar.isDisplayed()) {
                    return true;
                }
            } catch (StaleElementReferenceException e) {
                continue;
            }
        }
        return false;
    }

    private void fill(WebElement input, String value) {
        waitUntilElementClickable(input);
        input.clear();
        input.sendKeys(value);
    }
}
