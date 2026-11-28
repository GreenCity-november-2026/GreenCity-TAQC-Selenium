package com.greencity.ui.component.header;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.modal.auth.AuthModal;
import com.greencity.ui.page.about.AboutPage;
import com.greencity.ui.page.events.EventsListPage;
import com.greencity.ui.page.news.NewsListPage;
import com.greencity.ui.page.places.PlacesPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;
import java.util.List;
import java.util.ArrayList;


public class HeaderComponent extends BaseComponent {

    private static final By SIGN_IN = By.cssSelector(
            "a.header_sign-in-link, img.ubs-header-sing-in-img-greencity, "
                    + ".header_mobile-menu-sign-in a");
    private static final By SIGN_UP = By.cssSelector(
            ".header_sign-up-link, .header_mobile-menu-sign-up");
    private static final By BURGER = By.cssSelector(
            ".header_burger-btn .menu-icon-wrapper");
    private static final By LANGUAGE = By.cssSelector(
            "[aria-label='language switcher']");
    private static final By CURRENT_LANGUAGE = By.cssSelector(
            "[aria-label='language switcher'] [role='option'] span");
    private static final By WARNING = By.cssSelector(
            "button.warning_button_comment");

    @Getter
    @FindBy(xpath = ".//img[@src='assets/img/logo.svg']")
    private WebElement logo;

    public HeaderComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public AuthModal openSignIn() {
        clickHeaderControl(SIGN_IN);
        return new AuthModal(driver).waitUntilSignInOpen();
    }

    public AuthModal openSignUp() {
        clickHeaderControl(SIGN_UP);
        return new AuthModal(driver).waitUntilSignUpOpen();
    }

    public AuthModal openMySpace() {
        clickHeaderControl(routeLink("/greenCity/profile"));
        return new AuthModal(driver).waitUntilSignInOpen();
    }

    public NewsListPage openEcoNews() {
        openSection("/greenCity/news");
        return new NewsListPage(driver);
    }

    public EventsListPage openEvents() {
        openSection("/greenCity/events");
        return new EventsListPage(driver);
    }

    public PlacesPage openPlaces() {
        openSection("/greenCity/places");
        return new PlacesPage(driver);
    }

    public AboutPage openAbout() {
        openSection("/greenCity/about");
        return new AboutPage(driver);
    }

    public void openUbs() {
        openSection("/ubs");
    }

    public String getLanguage() {
        return wait.until(d -> visibleControl(CURRENT_LANGUAGE)).getText().trim();
    }

    public void selectLanguage(String language) {
        if (!"En".equals(language) && !"Uk".equals(language)) {
            throw new IllegalArgumentException("Supported languages: En, Uk");
        }

        if (language.equals(getLanguage())) {
            return;
        }

        WebElement switcher = wait.until(d -> visibleControl(LANGUAGE));
        if (!"true".equals(switcher.getDomAttribute("aria-expanded"))) {
            wait.until(d -> visibleControl(CURRENT_LANGUAGE)).click();
        }

        By option = By.cssSelector(
                "[aria-label='language switcher'] [role='menuitem'][aria-label='"
                        + language + "']");
        wait.until(d -> visibleControl(option)).click();

        wait.until(d -> {
            WebElement current = visibleControl(CURRENT_LANGUAGE);
            return current != null && language.equals(current.getText().trim());
        });
    }

    public boolean isLogoVisible() {
        return waitForVisibleElement(By.cssSelector("img[src='assets/img/logo.svg']"));
    }

    public boolean isSignInVisible() {
        return waitForVisibleElement(SIGN_IN);
    }

    public boolean isSignUpVisible() {
        return waitForVisibleElement(SIGN_UP);
    }

    public boolean isLanguageSwitcherVisible() {
        return waitForVisibleElement(LANGUAGE);
    }

    private boolean waitForVisibleElement(By locator) {
        try {
            return wait.until(ExpectedConditions.refreshed(d ->
                    rootElement.findElements(locator).stream()
                            .anyMatch(WebElement::isDisplayed)));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public List<String> getAvailableLanguages() {
        List<String> languages = new ArrayList<>();
        languages.add(getLanguage());

        wait.until(d -> visibleControl(CURRENT_LANGUAGE)).click();

        By options = By.cssSelector(
                "[aria-label='language switcher'] [role='menuitem']");
        List<WebElement> items = wait.until(
                ExpectedConditions.visibilityOfNestedElementsLocatedBy(rootElement, options));

        for (WebElement item : items) {
            String language = item.getDomAttribute("aria-label");
            if (language != null && !language.isBlank() && !languages.contains(language)) {
                languages.add(language);
            }
        }

        wait.until(d -> visibleControl(CURRENT_LANGUAGE)).click();
        languages.sort(String::compareTo);
        return languages;
    }

    public boolean isLogoInViewport() {
        return logo.isDisplayed() && Boolean.TRUE.equals(threadJs.executeScript(
                "const r = arguments[0].getBoundingClientRect();" +
                        "return r.top >= 0 && r.bottom <= window.innerHeight;", logo));
    }

    public String getWarningText() {
        return wait.until(d -> {
            String text = rootElement.findElement(WARNING).getDomProperty("textContent");
            return text == null || text.isBlank() ? null : text.trim();
        });
    }

    private By routeLink(String route) {
        return By.cssSelector("nav a[href$='#" + route + "']");
    }

    private void openSection(String route) {
        clickHeaderControl(routeLink(route));
        wait.until(ExpectedConditions.urlMatches(".*#" + route + "/?$"));
    }

    private void clickHeaderControl(By locator) {
        if (visibleControl(locator) == null) {
            WebElement burger = visibleControl(BURGER);
            if (burger != null && !rootElement.findElements(
                    By.cssSelector(".menu-icon-active")).stream().anyMatch(WebElement::isDisplayed)) {
                burger.click();
            }
        }
        wait.until(d -> visibleControl(locator)).click();
    }

    private WebElement visibleControl(By locator) {
        for (WebElement element : rootElement.findElements(locator)) {
            if (element.isDisplayed() && element.isEnabled()) {
                return element;
            }
        }
        return null;
    }
}
