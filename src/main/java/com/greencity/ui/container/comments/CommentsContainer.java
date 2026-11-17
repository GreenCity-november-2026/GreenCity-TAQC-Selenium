package com.greencity.ui.container.comments;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.component.OptionalFind;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CommentsContainer extends BaseComponent {
    private final By totalCount = By.cssSelector("app-comments-container #total-count");

    public CommentsContainer(WebDriver driver) {
        super(driver, driver.findElement(By.tagName("body")));
    }

    public int getCount() {
        return OptionalFind.firstInt(OptionalFind.text(driver, totalCount));
    }
}
