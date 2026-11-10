package com.greencity.ui.page;

import com.greencity.ui.component.footer.FooterComponent;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class GreenCityPage extends BasePage {
    protected static final String GREEN_CITY_ROUTE = "#/greenCity";

    @Getter
    protected FooterComponent footer;

    @FindBy(xpath = "//footer")
    private WebElement footerRoot;

    public GreenCityPage(WebDriver driver) {
        super(driver);
        footer = new FooterComponent(driver, footerRoot);
    }

    public boolean isLoaded() {
        wait.until(ExpectedConditions.urlContains(GREEN_CITY_ROUTE));
        waitUntilElementVisible(footerRoot);
        return header.isDisplayed() && footer.isDisplayed();
    }
}
