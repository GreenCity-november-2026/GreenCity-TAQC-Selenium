package com.greencity.ui.page.homepage;


import com.greencity.ui.container.home.HomeEcoNews;
import com.greencity.ui.container.home.HomeHero;
import com.greencity.ui.container.home.HomeStats;
import com.greencity.ui.container.home.SubscribeForm;
import com.greencity.ui.page.GreenCityPage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends GreenCityPage {

    @Override
    protected String getRoute() {
        return ".*#/greenCity/?$";
    }

    @FindBy(css = "app-homepage header#header")
    private WebElement heroRoot;

    @FindBy(css = "app-homepage section#stats")
    private WebElement statsRoot;

    @FindBy(css = "app-homepage section#events")
    private WebElement ecoNewsRoot;

    @FindBy(css = "app-homepage section#subscription")
    private WebElement subscribeRoot;

    @Getter
    private final HomeHero hero;
    @Getter
    private final HomeStats stats;
    @Getter
    private final HomeEcoNews ecoNews;
    @Getter
    private final SubscribeForm subscribe;

    public HomePage(WebDriver driver) {
        super(driver);
        hero = new HomeHero(driver, heroRoot);
        stats = new HomeStats(driver, statsRoot);
        ecoNews = new HomeEcoNews(driver, ecoNewsRoot);
        subscribe = new SubscribeForm(driver, subscribeRoot);
    }

    public HomePage open(String url) {
        driver.get(url);
        return this;
    }

    @Override
    public boolean isLoaded() {
        return super.isLoaded() && hero.isDisplayed();
    }
}