package com.greencity.ui.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NoDataBlock extends BaseComponent {
    private final By root = By.cssSelector("app-no-data");
    private final By title = By.cssSelector("app-no-data h2");
    private final By text = By.cssSelector("app-no-data .description__advise p");

    public NoDataBlock(WebDriver driver) {
        super(driver, driver.findElement(By.tagName("body")));
    }

    @Override
    public boolean isDisplayed() {
        return OptionalFind.displayed(driver, root) != null;
    }

    public String getTitle() {
        return OptionalFind.text(driver, title);
    }

    public String getText() {
        return OptionalFind.text(driver, text);
    }
}
