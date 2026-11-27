package com.greencity.ui.component.header;

import com.greencity.ui.component.BaseComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HeaderComponent extends BaseComponent {

    @Getter
    @FindBy(xpath = ".//img[@src='assets/img/logo.svg']")
    private WebElement logo;

    @FindBy(xpath = ".//li[contains(@class,'lang-option')][normalize-space()='Ua' or normalize-space()='En']")
    private WebElement languageSwitcher;

    @FindBy(xpath = ".//li[contains(@class,'lang-option')]//span[normalize-space()='En']")
    private WebElement englishOption;

    public HeaderComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @Step("Get current language")
    public String getCurrentLanguage() {
        waitUntilElementVisible(languageSwitcher);
        return languageSwitcher.getText().trim();
    }

    @Step("Switch language to En")
    public HeaderComponent switchToEnglish() {
        if ("En".equals(getCurrentLanguage())) {
            return this;
        }
        waitUntilElementClickable(languageSwitcher);
        languageSwitcher.click();
        waitUntilElementClickable(englishOption);
        englishOption.click();
        wait.until(driver -> "En".equals(getCurrentLanguage()));
        return this;
    }
}
