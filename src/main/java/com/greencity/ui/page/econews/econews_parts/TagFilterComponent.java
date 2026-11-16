package com.greencity.ui.page.econews.econews_parts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

// Фільтр тегів над стрічкою новин
public class TagFilterComponent {
    private final WebDriver driver;
    private final WebElement parent;

    // Корінь фільтра тегів.
    public final By root = By.cssSelector("app-tag-filter");
    // Підпис блока фільтра.
    public final By header = By.cssSelector("app-tag-filter span.filter");
    // Кнопка одного тегу.
    public final By tagButton = By.cssSelector("app-tag-filter button.tag-button");
    // Видима назва тегу.
    public final By tagName = By.cssSelector("app-tag-filter button.tag-button span.text");
    // Тег, який зараз вибраний.
    public final By selectedTag = By.cssSelector("app-tag-filter a.global-tag-clicked span.text");

    public TagFilterComponent(WebDriver driver) {
        this(driver, null);
    }

    public TagFilterComponent(WebDriver driver, WebElement parent) {
        this.driver = driver;
        this.parent = parent;
    }

    // Читає назви всіх тегів
    public List<String> getTags() {
        return Dom.texts(context(), tagName);
    }

    // Читає назви тегів, які зараз увімкнені
    public List<String> getSelectedTags() {
        return Dom.texts(context(), selectedTag);
    }

    // Toggle увімкнути або вимкнути тег за видимою назвою.
    public void select(String tag) {
        for (WebElement button : context().findElements(tagButton)) {
            if (tag.equals(Dom.text(button, By.cssSelector("span.text")))) {
                button.click();
                return;
            }
        }
        throw new org.openqa.selenium.NoSuchElementException("Tag not found: " + tag);
    }

    private org.openqa.selenium.SearchContext context() {
        return Dom.context(driver, parent);
    }
}
