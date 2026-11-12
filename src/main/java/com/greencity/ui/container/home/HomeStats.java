package com.greencity.ui.container.home;

import com.greencity.ui.component.BaseComponent;
import io.qameta.allure.Step;
import com.greencity.ui.component.home.StatRow;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class HomeStats extends BaseComponent {
    @FindBy(css = "h2.section-caption")
    private WebElement caption;

    @FindBy(css = "app-stat-row")
    private List<WebElement> statRowRoots;

    public HomeStats(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @Step("Get number of Users")
    public String getUsersAmount() {
        waitUntilAllElementsVisible(statRowRoots);
        return caption.getText().replaceAll("\\D", "");
    }

    @Step("Get statistic rows")
    public List<StatRow> getRows() {
        waitUntilAllElementsVisible(statRowRoots);

        List<StatRow> rows = new ArrayList<>();
        for (WebElement root : statRowRoots) {
            rows.add(new StatRow(driver, root));
        }
        return rows;
    }

    @Step("Get statistics '{name}'")
    public String getStat(String name) {
        return getRows().stream()
                .map(StatRow::getTitle)
                .filter(title -> title.toLowerCase().contains(name.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No stat row contains: " + name));
    }
}
