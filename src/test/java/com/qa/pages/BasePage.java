package com.qa.pages;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.HashMap;

import static com.qa.BaseTest.platform;

public class BasePage {
    protected AppiumDriver driver;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
    }

    public void waitForVisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestUtils.WAIT));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void clear(WebElement element) {
        waitForVisibility(element);
        element.clear();
    }

    public void click(WebElement element) {
        waitForVisibility(element);
        element.click();
    }

    public void sendKeys(WebElement element, String text) {
        waitForVisibility(element);
        element.sendKeys(text);
    }

    public String getText(WebElement element) {
        switch (platform) {
            case "Android":
                return getAttribute(element, "text");
            case "iOS":
                return getAttribute(element, "label");
        }
        return null;
    }

    public WebElement scrollToElement() {
        switch (platform) {
            case "Android":
                return driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))" +
                                ".scrollIntoView(new UiSelector().description(\"test-Price\"));"));
            /*return driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().description(\"test-Inventory item page\"))" +
                            ".scrollIntoView(new UiSelector().description(\"test-Price\"));"));*/
            case "iOS":
                RemoteWebElement element = (RemoteWebElement) driver.findElement(AppiumBy.className("XCUIElementTypeScrollView"));
                String elementID = element.getId();
                HashMap<String, String> scrollObject = new HashMap<>();
                scrollObject.put("element", elementID);
                scrollObject.put("direction", "up");
                driver.executeScript("mobile:scroll", scrollObject);
                return element;
        }
        return null;
    }

    public String getAttribute(WebElement element, String attribute) {
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }
}
