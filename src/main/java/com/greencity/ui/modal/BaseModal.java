package com.greencity.ui.modal;

import com.greencity.ui.component.BaseComponent;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BaseModal extends BaseComponent {
    @FindBy(css = ".close-modal-window")
    private WebElement closeButton;

    public BaseModal(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public boolean isOpen() {
        try {
            return rootElement.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public void waitUntilOpen() {
        waitUntilElementVisible(rootElement);
    }

    public void waitUntilClosed() {
        wait.until(ExpectedConditions.invisibilityOf(rootElement));
    }

    public void close() {
        waitUntilElementClickable(closeButton);
        closeButton.click();
        waitUntilClosed();
    }
}
