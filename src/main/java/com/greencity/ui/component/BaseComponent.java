package com.greencity.ui.component;

import com.greencity.ui.Base;
import lombok.Getter;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;


public abstract class BaseComponent extends Base {

    @Getter
    protected WebElement rootElement;

    public BaseComponent(WebDriver driver, WebElement rootElement) {
        super(driver);
        PageFactory.initElements(new DefaultElementLocatorFactory(rootElement), this);
        this.rootElement = rootElement;
    }

    public boolean isDisplayed() {
        try {
            return  rootElement.isDisplayed();
        } catch (NoSuchElementException |StaleElementReferenceException e) {
            return false;
        }
    }
}