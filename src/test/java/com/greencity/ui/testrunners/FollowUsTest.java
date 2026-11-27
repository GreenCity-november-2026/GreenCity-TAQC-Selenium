package com.greencity.ui.testrunners;

import com.greencity.ui.component.footer.FooterComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FollowUsTest extends BaseTestRunner {

    private static final List<String> NETWORKS = List.of("Twitter", "LinkedIn", "Facebook", "Instagram", "YouTube");

    //Preconditions: Role: Guest, language: En
    @Test
    @Tag("smoke")
    @Tag("P0")
    @DisplayName("TC-P0-FTR-02. Follow us icons")
    void checkFollowUsIcons() {
        FooterComponent footer = homePage.getFooter().scrollIntoView();

        Map<String, String> hrefs = footer.getSocialHrefs();
        Map<String, Boolean> disabled = footer.getSocialDisabledStates();

        assertEquals(NETWORKS, List.copyOf(hrefs.keySet()), "All 5 icons should be present");

        List<Executable> checks = new ArrayList<>();
        for (String network : NETWORKS) {
            checks.add(() -> assertEquals("#", hrefs.get(network), "href of " + network + " icon should be '#'"));
            checks.add(() -> assertFalse(disabled.get(network), network + " icon should not be disabled"));
        }
        assertAll("Follow us icons", checks);

        assertEquals(1, driver.getWindowHandles().size(), "No new windows should be opened");
    }
}