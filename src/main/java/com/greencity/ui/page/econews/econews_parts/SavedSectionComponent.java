package com.greencity.ui.page.econews.econews_parts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Блок «Збережене» над стрічкою. З'являється, коли увімкнені закладки.
 * Тут лишається лише вкладка еко-новин: події і місця до цього POM не входять.
 */
public class SavedSectionComponent {
    private final WebDriver driver;
    private final WebElement parent;

    // Корінь блока збереженого.
    public final By root = By.cssSelector("app-saved-section");
    // Заголовок блока.
    public final By title = By.cssSelector("app-saved-section p.main-header");
    // Перша вкладка. У розмітці це вкладка еко-новин.
    public final By ecoNewsTab = By.cssSelector("app-saved-section button.tab");

    public SavedSectionComponent(WebDriver driver) {
        this(driver, null);
    }

    public SavedSectionComponent(WebDriver driver, WebElement parent) {
        this.driver = driver;
        this.parent = parent;
    }

    // Повідомляє, чи блок збереженого зараз на екрані
    public boolean isDisplayed() {
        return Dom.displayed(context(), root) != null;
    }

    // Читає заголовок блока збереженого.
    public String getTitle() {
        return Dom.text(context(), title);
    }

    // Відкриває вкладку еко-новин у блоці збереженого.
    public void openEcoNewsTab() {
        Dom.click(context(), ecoNewsTab);
    }

    // Повідомляє, чи вкладка еко-новин зараз активна.
    public boolean isEcoNewsTabActive() {
        return Dom.hasClass(Dom.displayed(context(), ecoNewsTab), "active-tab");
    }

    private org.openqa.selenium.SearchContext context() {
        return Dom.context(driver, parent);
    }
}
