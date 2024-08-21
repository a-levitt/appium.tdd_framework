package com.qa;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {

    protected static AppiumDriver driver;
    protected static Properties props;
    InputStream inputStream;

    public void setDriver(AppiumDriver driver) {
        this.driver = driver;
    }

    public AppiumDriver getDriver() {
        return driver;
    }

    public void waitForVisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestUtils.WAIT));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void click(WebElement element) {
        waitForVisibility(element);
        element.click();
    }

    public void sendKeys(WebElement element, String text) {
        waitForVisibility(element);
        element.sendKeys(text);
    }

    public String getAttribute(WebElement element, String attribute) {
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }

    public void initializeDriver(String platformName, String platformVersion, String deviceName) throws Exception {
        try {
            props = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);

            //URL appUrl = getClass().getClassLoader().getResource(props.getProperty("AndroidAppLocation"));
            String appPath =
            System.getProperty("user.dir") + File.separator + "src" + File.separator +
                    "main" + File.separator + "resources" + File.separator +
                    "app" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";

            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName(platformName)
                    .setPlatformVersion(platformVersion)
                    .setDeviceName(deviceName)
                    .setAutomationName(props.getProperty("AndroidAutomationName"))
                    //.setUdid(props.getProperty("deviceUdid"))
                    .setUnlockType(props.getProperty("deviceUnlockType"))
                    .setUnlockKey(props.getProperty("deviceUnlockKey"))
                    .setAppWaitActivity(props.getProperty("AndroidAppWaitActivity"))
                    .setAppPackage(props.getProperty("AndroidAppPackage"))
                    .setAppActivity(props.getProperty("AndroidAppActivity"))
                    //.setApp(appPath)
            ;

            URL url = new URL(props.getProperty("appiumURL"));

            driver = new AndroidDriver(url, options);
            //String sessionId = driver.getSessionId().toString();
            //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void quitDriver() {
        driver.quit();
    }
}
