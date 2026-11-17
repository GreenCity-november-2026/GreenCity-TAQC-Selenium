package com.greencity.ui.component;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class OptionalFind {
    private static final Pattern DIGITS = Pattern.compile("\\d+");

    private OptionalFind() {
    }

    public static String text(SearchContext context, By locator) {
        WebElement element = first(context, locator);
        if (element == null) {
            return "";
        }
        String value = element.getText();
        return value == null ? "" : value.trim();
    }

    public static List<String> texts(SearchContext context, By locator) {
        List<String> values = new ArrayList<>();
        for (WebElement element : context.findElements(locator)) {
            String value = element.getText();
            if (value == null) {
                continue;
            }
            String trimmed = value.trim();
            if (!trimmed.isEmpty()) {
                values.add(trimmed);
            }
        }
        return values;
    }

    public static int firstInt(String value) {
        if (value == null) {
            return 0;
        }
        Matcher matcher = DIGITS.matcher(value);
        return matcher.find() ? Integer.parseInt(matcher.group()) : 0;
    }

    public static WebElement first(SearchContext context, By locator) {
        List<WebElement> found = context.findElements(locator);
        return found.isEmpty() ? null : found.get(0);
    }

    public static WebElement displayed(SearchContext context, By locator) {
        for (WebElement element : context.findElements(locator)) {
            try {
                if (element.isDisplayed()) {
                    return element;
                }
            } catch (StaleElementReferenceException ignored) {
                continue;
            }
        }
        return null;
    }

    public static List<WebElement> displayedAll(SearchContext context, By locator) {
        List<WebElement> visible = new ArrayList<>();
        for (WebElement element : context.findElements(locator)) {
            try {
                if (element.isDisplayed()) {
                    visible.add(element);
                }
            } catch (StaleElementReferenceException ignored) {
                continue;
            }
        }
        return visible;
    }

    public static void click(SearchContext context, By locator) {
        WebElement element = displayed(context, locator);
        if (element == null) {
            throw new NoSuchElementException(locator.toString());
        }
        element.click();
    }

    public static boolean hasClass(WebElement element, String className) {
        if (element == null) {
            return false;
        }
        String classes = element.getAttribute("class");
        return classes != null && classes.contains(className);
    }
}
