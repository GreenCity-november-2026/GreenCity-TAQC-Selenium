package com.greencity.ui.component.footer;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.modal.auth.AuthModal;
import com.greencity.ui.page.about.AboutPage;
import com.greencity.ui.page.events.EventsListPage;
import com.greencity.ui.page.homepage.HomePage;
import com.greencity.ui.page.news.NewsListPage;
import com.greencity.ui.page.places.PlacesPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FooterComponent extends BaseComponent {

    private static final String HOME_ROUTE = ".*#/greenCity/?$";
    private static final String NEWS_ROUTE = ".*#/greenCity/news/?$";
    private static final String EVENTS_ROUTE = ".*#/greenCity/events/?$";
    private static final String PLACES_ROUTE = ".*#/greenCity/places/?$";
    private static final String ABOUT_ROUTE = ".*#/greenCity/about/?$";
    private static final String UBS_ROUTE = ".*#/ubs/?$";

    @FindBy(css = "div.footer_logo-wrp a")
    private WebElement logoLink;

    @FindBy(css = "a.footer_link-item[href='#/greenCity/news']")
    private WebElement ecoNewsLink;

    @FindBy(css = "a.footer_link-item[href='#/greenCity/events']")
    private WebElement eventsLink;

    @FindBy(css = "a.footer_link-item[href='#/greenCity/places']")
    private WebElement placesLink;

    @FindBy(css = "a.footer_link-item[href='#/greenCity/about']")
    private WebElement aboutLink;

    @FindBy(css = "a.footer_link-item[href*='/profile']")
    private WebElement mySpaceLink;

    @FindBy(css = "a.footer_link-item[href='#/ubs']")
    private WebElement ubsLink;

    @FindBy(css = "#copyright-label")
    private WebElement copyright;

    @FindBy(css = "a.footer_social-link")
    private List<WebElement> socialLinks;

    @FindBy(css = "a.footer_link-item")
    private List<WebElement> navLinks;

    @FindBy(css = ".footer_follow-us")
    private WebElement followUs;

    public FooterComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @Step("Open Eco News from the footer")
    public NewsListPage openEcoNews() {
        clickLink(ecoNewsLink);
        wait.until(ExpectedConditions.urlMatches(NEWS_ROUTE));
        return new NewsListPage(driver);
    }

    @Step("Open Events from the footer")
    public EventsListPage openEvents() {
        clickLink(eventsLink);
        wait.until(ExpectedConditions.urlMatches(EVENTS_ROUTE));
        return new EventsListPage(driver);
    }

    @Step("Open Places from the footer")
    public PlacesPage openPlaces() {
        clickLink(placesLink);
        wait.until(ExpectedConditions.urlMatches(PLACES_ROUTE));
        return new PlacesPage(driver);
    }

    @Step("Open About Us from the footer")
    public AboutPage openAbout() {
        clickLink(aboutLink);
        wait.until(ExpectedConditions.urlMatches(ABOUT_ROUTE));
        return new AboutPage(driver);
    }

    @Step("Open Home from the footer logo")
    public HomePage openHome() {
        clickLink(logoLink);
        wait.until(ExpectedConditions.urlMatches(HOME_ROUTE));
        return new HomePage(driver);
    }

    @Step("Open My Space from the footer as a guest")
    public AuthModal openMySpace() {
        clickLink(mySpaceLink);
        return new AuthModal(driver);
    }

    @Step("Open UBS Courier from the footer")
    public void openUbs() {
        clickLink(ubsLink);
        wait.until(ExpectedConditions.urlMatches(UBS_ROUTE));
    }

    @Step("Get footer copyright")
    public String getCopyright() {
        waitUntilElementVisible(copyright);
        return copyright.getText().trim();
    }

    @Step("Get footer social links")
    public Map<String, String> getSocialHrefs() {
        Map<String, String> hrefs = new LinkedHashMap<>();
        for (WebElement link : socialLinks) {
            String alt = link.findElement(By.tagName("img")).getDomAttribute("alt");
            String name = alt == null ? "" : alt.replace(" link", "").trim();
            hrefs.put(name, link.getDomAttribute("href"));
        }
        return hrefs;
    }

    public Map<String, Boolean> getSocialDisabledStates() {
        Map<String, Boolean> states = new LinkedHashMap<>();
        for (WebElement link : socialLinks) {
            String alt = link.findElement(By.tagName("img")).getDomAttribute("alt");
            String name = alt == null ? "" :alt.replace(" link", "").trim();
            boolean disabled = link.getDomAttribute("disabled") != null;

            states.put(name, disabled);
        }
        return states;
    }

    @Step("Scroll to the footer")
    public FooterComponent scrollIntoView() {
        scrollToElementJs(rootElement);
        return this;
    }

    @Step("Get footer 'Follow us' text")
    public String getFollowUsText() {
        waitUntilElementVisible(followUs);
        return followUs.getText().trim();
    }

    @Step("Get footer navigation links")
    public Map<String, String> getNavLinks() {
        waitUntilAllElementsVisible(navLinks);
        Map<String, String> links = new LinkedHashMap<>();
        for (WebElement link :navLinks) {
            links.put(link.getText().trim(), link.getDomAttribute("href"));
        }
        return links;
    }

    private void clickLink(WebElement link) {
        scrollToElementJs(link);
        link.click();
    }
}
