package com.greencity.ui.page.econews.econews_parts;

import com.greencity.ui.page.econews.EcoNews;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// Форма створення і редагування новини: #/greenCity/news/create-news.
public class CreateEditNewsPage extends EcoNews {
    public static final String HASH = "#/greenCity/news/create-news";

    // Корінь форми.
    public final By root = By.cssSelector("app-create-edit-news");
    // Заголовок форми: створення або редагування.
    public final By formTitle = By.cssSelector("app-create-edit-news h2.title-header");
    // Поле заголовка новини, до 170 символів.
    public final By titleInput = By.cssSelector("app-create-edit-news textarea[formcontrolname='title']");
    // Лічильник довжини заголовка.
    public final By titleLimit = By.cssSelector("app-create-edit-news .title-block .field-info");
    // Поле джерела новини.
    public final By sourceInput = By.cssSelector("app-create-edit-news input[formcontrolname='source']");
    // Кнопка тега у формі.
    public final By tagButton = By.cssSelector("app-create-edit-news app-tags-select button.tag-button");
    // Вибраний тег форми.
    public final By selectedTag = By.cssSelector("app-create-edit-news app-tags-select a.global-tag-clicked span.text");
    // Поле файлу зображення.
    public final By imageInput = By.cssSelector("app-create-edit-news input#upload");
    // Редактор тексту новини.
    public final By content = By.cssSelector("app-create-edit-news .ql-editor");
    // Лічильник довжини тексту.
    public final By contentCounter = By.cssSelector("app-create-edit-news .quill-counter");
    // Кнопка скасування.
    public final By cancelButton = By.cssSelector("app-create-edit-news .submit-buttons button.tertiary-global-button");
    // Кнопка перегляду перед публікацією.
    public final By previewButton = By.cssSelector("app-create-edit-news .submit-buttons button.secondary-global-button");
    // Кнопка публікації або збереження.
    public final By submitButton = By.cssSelector("app-create-edit-news .submit-buttons button.primary-global-button");
    // Екран очікування, поки новину публікують.
    public final By postingLoader = By.cssSelector("app-post-news-loader");

    public CreateEditNewsPage(WebDriver driver) {
        this(driver, null);
    }

    public CreateEditNewsPage(WebDriver driver, WebElement parent) {
        super(driver, parent);
    }

    // Відкриває форму створення новини.
    public CreateEditNewsPage open() {
        String url = driver.getCurrentUrl();
        int hash = url.indexOf('#');
        String base = hash >= 0 ? url.substring(0, hash) : url;
        driver.get(base + HASH);
        return this;
    }

    // Заголовок самої форми.
    public String getFormTitle() {
        return Dom.text(context(), formTitle);
    }

    // Заголовок новини.
    public CreateEditNewsPage setTitle(String title) {
        WebElement input = Dom.displayed(context(), titleInput);
        input.clear();
        input.sendKeys(title);
        return this;
    }

    // заголовок, який зараз стоїть у формі.
    public String getTitle() {
        WebElement input = Dom.first(context(), titleInput);
        if (input == null) {
            return "";
        }
        String value = input.getAttribute("value");
        return value == null ? "" : value;
    }

    // Вводить адресу джерела.
    public CreateEditNewsPage setSource(String source) {
        WebElement input = Dom.displayed(context(), sourceInput);
        input.clear();
        input.sendKeys(source);
        return this;
    }

    // тег за видимою назвою
    public CreateEditNewsPage selectTag(String tag) {
        for (WebElement button : context().findElements(tagButton)) {
            if (tag.equals(Dom.text(button, By.cssSelector("span.text")))) {
                button.click();
                return this;
            }
        }
        throw new org.openqa.selenium.NoSuchElementException("Tag not found: " + tag);
    }

    // Читає теги, які вже вибрані у формі.
    public java.util.List<String> getSelectedTags() {
        return Dom.texts(context(), selectedTag);
    }

    // Зображення новини у поле файлу.
    public CreateEditNewsPage uploadImage(String absolutePath) {
        WebElement input = Dom.first(context(), imageInput);
        input.sendKeys(absolutePath);
        return this;
    }

    // Вводить текст новини в редактор.
    public CreateEditNewsPage setContent(String text) {
        WebElement editor = Dom.displayed(context(), content);
        editor.click();
        editor.sendKeys(text);
        return this;
    }

    // Читає текст у редакторі
    public String getContent() {
        return Dom.text(context(), content);
    }

    // Скасовує створення або редагування.
    public void cancel() {
        Dom.click(context(), cancelButton);
    }

    // Відкриває попередній перегляд новини перед публікацією.
    public NewsPreviewPage preview() {
        Dom.click(context(), previewButton);
        return new NewsPreviewPage(driver);
    }

    // Публікує новину або зберігає зміни.
    public void publish() {
        Dom.click(context(), submitButton);
    }

    // Повідомляє, чи новину зараз публікують.
    public boolean isPosting() {
        return Dom.displayed(context(), postingLoader) != null;
    }
}
