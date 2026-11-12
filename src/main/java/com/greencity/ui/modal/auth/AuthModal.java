package com.greencity.ui.modal.auth;

import com.greencity.ui.modal.BaseModal;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthModal extends BaseModal {

    private static final By ROOT = By.cssSelector("app-auth-modal");

    @FindBy(css = ".close-modal-window")
    private WebElement closeButton;

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
}