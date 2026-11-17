package com.greencity.ui.page.news;

import com.greencity.ui.container.news.NewsEditorForm;
import com.greencity.ui.page.GreenCityPage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CreateEditNewsPage extends GreenCityPage {
    private static final String HASH = "#/greenCity/news/create-news";

    @Getter
    private final NewsEditorForm form;

    public CreateEditNewsPage(WebDriver driver) {
        super(driver);
        this.form = new NewsEditorForm(driver);
    }

    @Override
    protected String getRoute() {
        return ".*#/greenCity/news/create-news(?:[/?#].*)?$";
    }

    public CreateEditNewsPage open() {
        String url = driver.getCurrentUrl();
        int hash = url.indexOf('#');
        String base = hash >= 0 ? url.substring(0, hash) : url;
        driver.get(base + HASH);
        return this;
    }

    public String getFormTitle() {
        return form.getFormTitle();
    }

    public CreateEditNewsPage setTitle(String title) {
        form.setTitle(title);
        return this;
    }

    public String getTitle() {
        return form.getTitle();
    }

    public CreateEditNewsPage setSource(String source) {
        form.setSource(source);
        return this;
    }

    public CreateEditNewsPage selectTag(String tag) {
        form.selectTag(tag);
        return this;
    }

    public List<String> getSelectedTags() {
        return form.getSelectedTags();
    }

    public CreateEditNewsPage uploadImage(String absolutePath) {
        form.uploadImage(absolutePath);
        return this;
    }

    public CreateEditNewsPage setContent(String text) {
        form.setContent(text);
        return this;
    }

    public String getContent() {
        return form.getContent();
    }

    public void cancel() {
        form.cancel();
    }

    public NewsDetailPage preview() {
        form.openPreview();
        return new NewsDetailPage(driver);
    }

    public NewsDetailPage publish() {
        form.submit();
        return new NewsDetailPage(driver);
    }

    public boolean isPosting() {
        return form.isPosting();
    }
}
