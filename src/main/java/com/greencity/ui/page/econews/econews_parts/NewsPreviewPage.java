package com.greencity.ui.page.econews.econews_parts;

import com.greencity.ui.page.econews.EcoNews;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

// Попередній перегляд новини перед публікацією: #/greenCity/news/preview.
public class NewsPreviewPage extends EcoNews {
    public static final String HASH = "#/greenCity/news/preview";

    // Корінь попереднього перегляду.
    public final By root = By.cssSelector("app-news-preview-page");
    // Повернення до форми редагування.
    public final By backLink = By.cssSelector("app-news-preview-page a.button-link");
    // Кнопка публікації з перегляду.
    public final By publishButton = By.cssSelector("app-news-preview-page button.primary-global-button");
    // Підпис режиму перегляду.
    public final By previewTitle = By.cssSelector("app-news-preview-page .create-news-text");
    // Тег у перегляді.
    public final By tags = By.cssSelector("app-news-preview-page .tags-item");
    // Заголовок новини в перегляді.
    public final By title = By.cssSelector("app-news-preview-page .news-title");
    // Дата, яка буде в опублікованій новині.
    public final By date = By.cssSelector("app-news-preview-page .news-info-date");
    // Автор у перегляді.
    public final By author = By.cssSelector("app-news-preview-page .news-info-author");
    // Зображення новини в перегляді.
    public final By image = By.cssSelector("app-news-preview-page img.news-image-img");
    // Текст новини в перегляді.
    public final By body = By.cssSelector("app-news-preview-page .news-text-content");
    // Джерело в перегляді.
    public final By source = By.cssSelector("app-news-preview-page .source-text");
    // Екран очікування під час публікації.
    public final By postingLoader = By.cssSelector("app-post-news-loader");

    public NewsPreviewPage(WebDriver driver) {
        this(driver, null);
    }

    public NewsPreviewPage(WebDriver driver, WebElement parent) {
        super(driver, parent);
    }

    // Читає заголовок новини так, як його побачить читач.
    public String getTitle() {
        return Dom.text(context(), title);
    }

    // Читає теги в перегляді.
    public List<String> getTags() {
        return Dom.texts(context(), tags);
    }

    // Читає дату, яку перегляд підставляє в новину.
    public String getDate() {
        return Dom.text(context(), date);
    }

    // Читає автора в перегляді.
    public String getAuthor() {
        return Dom.text(context(), author);
    }

    // Читає текст новини в перегляді.
    public String getText() {
        return Dom.text(context(), body);
    }

    // Читає джерело в перегляді.
    public String getSource() {
        return Dom.text(context(), source);
    }

    // Повертає з перегляду назад у форму.
    public CreateEditNewsPage backToEdit() {
        Dom.click(context(), backLink);
        return new CreateEditNewsPage(driver);
    }

    // Публікує новину з перегляду.
    public void publish() {
        Dom.click(context(), publishButton);
    }

    // Повідомляє, чи новину зараз публікують.
    public boolean isPosting() {
        return Dom.displayed(context(), postingLoader) != null;
    }
}
