package com.greencity.ui.component;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TagFilter extends BaseComponent {
    private final By header = By.cssSelector("app-tag-filter span.filter");
    private final By tagButton = By.cssSelector("app-tag-filter button.tag-button");
    private final By tagName = By.cssSelector("app-tag-filter button.tag-button span.text");
    private final By selectedTag = By.cssSelector("app-tag-filter a.global-tag-clicked span.text");

    public TagFilter(WebDriver driver) {
        super(driver, driver.findElement(By.tagName("body")));
    }

    public String getHeader() {
        return OptionalFind.text(driver, header);
    }

    public List<String> getTags() {
        return OptionalFind.texts(driver, tagName);
    }

    public List<String> getSelectedTags() {
        return OptionalFind.texts(driver, selectedTag);
    }

    public void select(String tag) {
        for (WebElement button : driver.findElements(tagButton)) {
            if (tag.equals(OptionalFind.text(button, By.cssSelector("span.text")))) {
                button.click();
                return;
            }
        }
        throw new NoSuchElementException("Tag not found: " + tag);
    }
}
