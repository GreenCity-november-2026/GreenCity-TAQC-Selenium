package com.greencity.ui.container.news;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.component.OptionalFind;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NewsEditorForm extends BaseComponent {
    private final By formTitle = By.cssSelector("app-create-edit-news h2.title-header");
    private final By titleInput = By.cssSelector("app-create-edit-news textarea[formcontrolname='title']");
    private final By sourceInput = By.cssSelector("app-create-edit-news input[formcontrolname='source']");
    private final By tagButton = By.cssSelector("app-create-edit-news app-tags-select button.tag-button");
    private final By selectedTag = By.cssSelector("app-create-edit-news app-tags-select a.global-tag-clicked span.text");
    private final By imageInput = By.cssSelector("app-create-edit-news input#upload");
    private final By content = By.cssSelector("app-create-edit-news .ql-editor");
    private final By cancelButton = By.cssSelector("app-create-edit-news .submit-buttons button.tertiary-global-button");
    private final By previewButton = By.cssSelector("app-create-edit-news .submit-buttons button.secondary-global-button");
    private final By submitButton = By.cssSelector("app-create-edit-news .submit-buttons button.primary-global-button");
    private final By postingLoader = By.cssSelector("app-post-news-loader");

    public NewsEditorForm(WebDriver driver) {
        super(driver, driver.findElement(By.tagName("body")));
    }

    public String getFormTitle() {
        return OptionalFind.text(driver, formTitle);
    }

    public NewsEditorForm setTitle(String title) {
        WebElement input = OptionalFind.displayed(driver, titleInput);
        input.clear();
        input.sendKeys(title);
        return this;
    }

    public String getTitle() {
        WebElement input = OptionalFind.first(driver, titleInput);
        if (input == null) {
            return "";
        }
        String value = input.getAttribute("value");
        return value == null ? "" : value;
    }

    public NewsEditorForm setSource(String source) {
        WebElement input = OptionalFind.displayed(driver, sourceInput);
        input.clear();
        input.sendKeys(source);
        return this;
    }

    public NewsEditorForm selectTag(String tag) {
        for (WebElement button : driver.findElements(tagButton)) {
            if (tag.equals(OptionalFind.text(button, By.cssSelector("span.text")))) {
                button.click();
                return this;
            }
        }
        throw new NoSuchElementException("Tag not found: " + tag);
    }

    public List<String> getSelectedTags() {
        return OptionalFind.texts(driver, selectedTag);
    }

    public NewsEditorForm uploadImage(String absolutePath) {
        WebElement input = OptionalFind.first(driver, imageInput);
        input.sendKeys(absolutePath);
        return this;
    }

    public NewsEditorForm setContent(String text) {
        WebElement editor = OptionalFind.displayed(driver, content);
        editor.click();
        editor.sendKeys(text);
        return this;
    }

    public String getContent() {
        return OptionalFind.text(driver, content);
    }

    public void cancel() {
        OptionalFind.click(driver, cancelButton);
    }

    public void openPreview() {
        OptionalFind.click(driver, previewButton);
    }

    public void submit() {
        OptionalFind.click(driver, submitButton);
    }

    public boolean isPosting() {
        return OptionalFind.displayed(driver, postingLoader) != null;
    }
}
