# GreenCity Automated Tests

Automated testing skeleton for the [GreenCity](https://www.greencity.cx.ua/#/greenCity) web application.

This repository is a **starting template for students**. It contains a Page Object Model (POM) structure, base test infrastructure, and one sample UI test. Your task is to add pages, components, and tests.

## Technologies

- **Java 21**
- **JUnit 5** — test framework
- **Selenium WebDriver** — UI automation
- **Maven** — dependency management
- **Allure** — test reporting
- **Cucumber** — BDD (optional, for later tasks)
- **Rest Assured** — API tests (optional, for later tasks)

## Quick Start

### 1. Clone the repository

```bash
git clone https://github.com/UA-4869-4827/GreenCity_TestCases.git
cd GreenCity_TestCases
```

### 2. Configure test data

Copy the example config and fill in your values:

```bash
cp src/test/resources/config.properties.example src/test/resources/config.properties
```

Edit `src/test/resources/config.properties`:

```properties
base.ui.url=https://www.greencity.cx.ua/#/greenCity
base.api.url=https://api-greencity.azurewebsites.net/

user.email=your.user@example.com
user.name=Your User Name
user.password=your_password

admin.email=admin@example.com
admin.name=Admin Name
admin.password=admin_password
```

> `config.properties` is in `.gitignore` — do not commit credentials.

`base.ui.url` must open the GreenCity module (`#/greenCity`). The site root can redirect to UBS, which this starter does not cover.

Waits are explicit. `Base` already creates a `WebDriverWait` of 10 seconds. Do not set an implicit wait on the driver: together with the explicit wait it makes element lookups unpredictable.

### 3. Install dependencies

```bash
mvn clean install -DskipTests
```

### 4. Run the sample test

```bash
mvn -Dtest=BaseTest test
```

Run all tests:

```bash
mvn test
```

Run a specific test class:

```bash
mvn -Dtest=YourTestClass test
```

## Project Structure

```
src/
├── main/java/com/greencity/
│   ├── ui/
│   │   ├── Base.java                    # common WebDriver helpers
│   │   ├── page/
│   │   │   ├── BasePage.java            # base class for all pages
│   │   │   └── homepage/HomePage.java   # example page — extend this pattern
│   │   ├── component/
│   │   │   ├── BaseComponent.java
│   │   │   ├── header/HeaderComponent.java
│   │   │   └── footer/FooterComponent.java
│   │   └── elements/BaseElement.java
│   └── api/
│       └── clients/BaseClient.java      # base class for API clients
└── test/java/com/greencity/
    ├── ui/
    │   ├── testrunners/BaseTestRunner.java  # WebDriver setup/teardown
    │   └── BaseTest.java                    # sample test
    ├── api/testRunners/ApiTestRunner.java
    ├── cucumber/
    │   ├── TestRunnerCucumber.java
    │   └── steps/BaseStep.java
    └── utils/TestValueProvider.java         # reads config.properties
```

## How to Add Your Code

### New Page Object

A page is a screen with its own URL under `#/greenCity`. Sign in, sign up, and "forgot password" are not pages: the header opens them as one modal, and the URL does not change. Add that later as a modal, not as `SignInPage`.

1. Create a class in `src/main/java/com/greencity/ui/page/<pagename>/`
2. Extend `BasePage`
3. Add `@FindBy` locators and page methods

Example for `#/greenCity/about`:

```java
package com.greencity.ui.page.about;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AboutPage extends BasePage {

    @FindBy(css = "h2.section__header")
    private WebElement title;

    public AboutPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return title.getText();
    }
}
```

### New Component

1. Create a class in `src/main/java/com/greencity/ui/component/<name>/`
2. Extend `BaseComponent`
3. Pass the root `WebElement` from the parent page

### New UI Test

1. Create a test class in `src/test/java/com/greencity/ui/`
2. Extend `BaseTestRunner`
3. Use JUnit 5 annotations (`@Test`, `@BeforeEach`, etc.)

Example:

```java
package com.greencity.ui;

import com.greencity.ui.page.about.AboutPage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class AboutTest extends BaseTestRunner {

    @Test
    void aboutTitleShouldBeVisible() {
        AboutPage aboutPage = homePage.getHeader().openAbout();
        assertFalse(aboutPage.getTitle().isBlank());
    }
}
```

`openAbout()` is a method you add on `HeaderComponent`. It clicks the About us link and returns `new AboutPage(driver)`.

### Cucumber Feature (optional)

1. Add `.feature` files to `src/test/resources/features/`
2. Add step definitions in `src/test/java/com/greencity/cucumber/steps/`
3. Run with `mvn -Dtest=TestRunnerCucumber test`

## Allure Report

```bash
mvn test
allure serve target/allure-results
```

## Requirements

- Java 21+
- Maven 3.8+
- Google Chrome (WebDriver is managed automatically via WebDriverManager)
