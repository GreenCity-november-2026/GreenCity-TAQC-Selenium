package com.greencity.ui.page.econews.econews_parts;

import com.greencity.ui.page.econews.EcoNews;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

// Сторінка однієї еко-новини: #/greenCity/news/{id}.
public class EcoNewsDetailPage extends EcoNews {
    // Корінь сторінки новини.
    public final By root = By.cssSelector("app-eco-news-detail");
    // Посилання назад до стрічки.
    public final By backLink = By.cssSelector("app-eco-news-detail a.button-link");
    // Тег новини.
    public final By tags = By.cssSelector("app-eco-news-detail .tags-item");
    // Заголовок новини.
    public final By title = By.cssSelector("app-eco-news-detail .news-title");
    // Дата публікації.
    public final By date = By.cssSelector("app-eco-news-detail .news-info-date");
    // Автор новини.
    public final By author = By.cssSelector("app-eco-news-detail .news-info-author");
    // Кнопка вподобання.
    public final By likeButton = By.cssSelector("app-eco-news-detail img.news_like");
    // Число вподобань.
    public final By likesCount = By.cssSelector("app-eco-news-detail .numerosity_likes");
    // Головне зображення новини.
    public final By image = By.cssSelector("app-eco-news-detail img.news-image-img");
    // Повний текст новини.
    public final By body = By.cssSelector("app-eco-news-detail .news-text-content");
    // Посилання на джерело новини.
    public final By sourceLink = By.cssSelector("app-eco-news-detail a.source-text");
    // Іконки поширення в соцмережі.
    public final By shareLinks = By.cssSelector("app-eco-news-detail .news-links-img");
    // Кнопка видалення. Видна лише автору новини.
    public final By deleteButton = By.cssSelector("app-eco-news-detail button.delete-news-button");
    // Посилання на редагування. Видне лише автору новини.
    public final By editLink = By.cssSelector("app-eco-news-detail .edit-news");

    public final EcoNewsWidgetComponent relatedNews;
    public final CommentsContainerComponent comments;

    public EcoNewsDetailPage(WebDriver driver) {
        this(driver, null);
    }

    public EcoNewsDetailPage(WebDriver driver, WebElement parent) {
        super(driver, parent);
        this.relatedNews = new EcoNewsWidgetComponent(driver, parent);
        this.comments = new CommentsContainerComponent(driver, parent);
    }

    // Читає заголовок відкритої новини.
    public String getTitle() {
        return Dom.text(context(), title);
    }

    // Читає теги відкритої новини.
    public List<String> getTags() {
        return Dom.texts(context(), tags);
    }

    // Читає дату публікації.
    public String getDate() {
        return Dom.text(context(), date);
    }

    // Читає рядок автора.
    public String getAuthor() {
        return Dom.text(context(), author);
    }

    // Читає кількість вподобань новини.
    public int getLikesCount() {
        return Dom.firstInt(Dom.text(context(), likesCount));
    }

    // Повідомляє, чи поточний користувач уже вподобав новину.
    public boolean isLiked() {
        WebElement button = Dom.displayed(context(), likeButton);
        if (button == null) {
            return false;
        }
        String src = button.getAttribute("src");
        return src != null && src.contains("liked.png");
    }

    // Ставить або знімає вподобання новини.
    public void like() {
        Dom.click(context(), likeButton);
    }

    // Читає повний текст новини.
    public String getText() {
        return Dom.text(context(), body);
    }

    // Читає адресу джерела новини.
    public String getSourceUrl() {
        WebElement link = Dom.first(context(), sourceLink);
        if (link == null) {
            return "";
        }
        String href = link.getAttribute("href");
        return href == null ? "" : href;
    }

    // Повертає на стрічку новин.
    public EcoNewsPage back() {
        Dom.click(context(), backLink);
        return new EcoNewsPage(driver);
    }

    // Повідомляє, чи автору новини доступні редагування і видалення.
    public boolean canEdit() {
        return Dom.displayed(context(), editLink) != null;
    }

    // Відкриває форму редагування цієї новини.
    public CreateEditNewsPage edit() {
        Dom.click(context(), editLink);
        return new CreateEditNewsPage(driver);
    }

    // Натискає кнопку видалення новини.
    public void delete() {
        Dom.click(context(), deleteButton);
    }
}
