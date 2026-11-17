package com.greencity.ui.page.news;

import com.greencity.ui.component.OptionalFind;
import com.greencity.ui.container.comments.CommentsContainer;
import com.greencity.ui.container.news.NewsContent;
import com.greencity.ui.container.news.RelatedNews;
import com.greencity.ui.page.GreenCityPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewsDetailPage extends GreenCityPage {
    private static final By DETAIL_BACK = By.cssSelector("app-eco-news-detail a.button-link");
    private static final By PREVIEW_BACK = By.cssSelector("app-news-preview-page a.button-link");
    private static final By PUBLISH = By.cssSelector("app-news-preview-page button.primary-global-button");
    private static final By EDIT = By.cssSelector("app-eco-news-detail .edit-news");
    private static final By DELETE = By.cssSelector("app-eco-news-detail button.delete-news-button");

    @Getter
    private final NewsContent content;
    @Getter
    private final RelatedNews related;
    @Getter
    private final CommentsContainer comments;

    public NewsDetailPage(WebDriver driver) {
        super(driver);
        this.content = new NewsContent(driver);
        this.related = new RelatedNews(driver);
        this.comments = new CommentsContainer(driver);
    }

    @Override
    protected String getRoute() {
        return ".*#/greenCity/news/(?:\\d+|preview)(?:[/?#].*)?$";
    }

    public boolean isPreview() {
        return content.isPreview();
    }

    public String getTitle() {
        return content.getTitle();
    }

    public String getAuthor() {
        return content.getAuthor();
    }

    public String getText() {
        return content.getText();
    }

    public NewsListPage back() {
        OptionalFind.click(driver, DETAIL_BACK);
        return new NewsListPage(driver);
    }

    public CreateEditNewsPage backToEditor() {
        OptionalFind.click(driver, PREVIEW_BACK);
        return new CreateEditNewsPage(driver);
    }

    public CreateEditNewsPage edit() {
        OptionalFind.click(driver, EDIT);
        return new CreateEditNewsPage(driver);
    }

    public void delete() {
        OptionalFind.click(driver, DELETE);
    }

    public boolean canEdit() {
        return OptionalFind.displayed(driver, EDIT) != null;
    }

    public NewsDetailPage publish() {
        if (!isPreview()) {
            throw new IllegalStateException("Publish is available from the news preview only.");
        }
        OptionalFind.click(driver, PUBLISH);
        return this;
    }

    public boolean isPosting() {
        return OptionalFind.displayed(driver, By.cssSelector("app-post-news-loader")) != null;
    }
}
