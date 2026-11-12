package com.greencity.ui.modal;

import com.greencity.ui.component.BaseComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseModal extends BaseComponent {
    private static final Duration OPEN_TIMEOUT = Duration.ofSeconds(10);

    public BaseModal(WebDriver driver, By rootLocator) {
        super(driver, waitForRoot(driver, rootLocator));
    }

    private static WebElement waitForRoot(WebDriver driver, By rootLocator) {
        return new WebDriverWait(driver, OPEN_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(rootLocator));
    }

    public boolean isOpen() {
        return isDisplayed();
    }

    @Step
    public void close() {
        actions.sendKeys(Keys.ESCAPE).perform();
        waitUntilElementInvisible(rootElement);
    }

}