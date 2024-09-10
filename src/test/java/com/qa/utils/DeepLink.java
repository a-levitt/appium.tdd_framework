package com.qa.utils;

import com.qa.BaseTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;

public class DeepLink {

    public static void OpenAppWith(String url){
        AppiumDriver driver = BaseTest.getDriver();

        switch(Objects.requireNonNull(BaseTest.getPlatform().toLowerCase())){
            case "android":
                HashMap<String, String> deepUrl = new HashMap<>();
                deepUrl.put("url", url);
                deepUrl.put("package", "com.swaglabsmobileapp");
                driver.executeScript("mobile: deepLink", deepUrl);
                break;
            case "ios":
                By urlBtn = AppiumBy.accessibilityId("CapsuleNavigationBar?isSelected=true");
                //         By urlFld = AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeTextField' && name CONTAINS 'URL'");
                By openBtn = AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' && name CONTAINS 'Open'");
                ((InteractsWithApps) driver).activateApp("com.apple.mobilesafari");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOfElementLocated(urlBtn)).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(urlBtn)).sendKeys("" + url + "\uE007");
                wait.until(ExpectedConditions.visibilityOfElementLocated(openBtn)).click();
                break;
        }
    }
}