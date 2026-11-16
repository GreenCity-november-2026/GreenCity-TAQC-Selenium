package com.greencity.ui.page.econews.econews_parts;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


final class Dom {
    private static final Pattern DIGITS = Pattern.compile("\\d+");

    private Dom() {
    }

    // Повертає область пошуку: переданий елемент або весь драйвер.
    static SearchContext context(WebDriver driver, WebElement parent) {
        return parent != null ? parent : driver;
    }

    // Повертає видимий текст першого елемента за локатором.
    static String text(SearchContext context, By locator) {
        WebElement element = first(context, locator);
        if (element == null) {
            return "";
        }
        String value = element.getText();
        return value == null ? "" : value.trim();
    }

    // Збирає видимі тексти всіх елементів за локатором.
    static List<String> texts(SearchContext context, By locator) {
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

    // Дістає перше ціле число з рядка лічильника.
    static int firstInt(String value) {
        if (value == null) {
            return 0;
        }
        Matcher matcher = DIGITS.matcher(value);
        return matcher.find() ? Integer.parseInt(matcher.group()) : 0;
    }

    //Повертає перший елемент за локатором або null
    static WebElement first(SearchContext context, By locator) {
        List<WebElement> found = context.findElements(locator);
        return found.isEmpty() ? null : found.get(0);
    }

   // Повертає перший видимий елемент за локатором.
    static WebElement displayed(SearchContext context, By locator) {
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

    // Повертає всі видимі елементи за локатором.
    static List<WebElement> displayedAll(SearchContext context, By locator) {
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

    // Клікаємо на перший видимий елемент за локатором.
    static void click(SearchContext context, By locator) {
        WebElement element = displayed(context, locator);
        if (element == null) {
            throw new NoSuchElementException(locator.toString());
        }
        element.click();
    }

    // Перевірка що в атрибуті class елемента є заданий клас.
    static boolean hasClass(WebElement element, String className) {
        if (element == null) {
            return false;
        }
        String classes = element.getAttribute("class");
        return classes != null && classes.contains(className);
    }
}
