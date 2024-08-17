package com.qa;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.*;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class BaseTest {

    protected AppiumDriver driver;
    protected Properties props;
    InputStream inputStream;

    @Test
    public void f() {
    }

/*
    @Test
    public void invalidUserName() {
        WebElement fieldUserName = driver.findElement(AppiumBy.accessibilityId("test-Username"));
        fieldUserName.sendKeys("123");
        WebElement fieldPassword = driver.findElement(AppiumBy.accessibilityId("test-Password"));
        fieldPassword.sendKeys("secret_sauce");
        WebElement buttonLogin = driver.findElement(AppiumBy.accessibilityId("test-LOGIN"));
        buttonLogin.click();

        //WebElement textError = driver.findElement(AppiumBy.accessibilityId("test-Error message"));
        WebElement textError = driver.findElement(AppiumBy.xpath( "//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]"));
        String actualErrorText = textError.getAttribute("text");

        Assert.assertEquals(actualErrorText, "Username and password do not match any user in this service.");
    }


    @Test
    public void invalidPassword() {
        WebElement fieldUserName = driver.findElement(AppiumBy.accessibilityId("test-Username"));
        fieldUserName.sendKeys("standard_user");
        WebElement fieldPassword = driver.findElement(AppiumBy.accessibilityId("test-Password"));
        fieldPassword.sendKeys("123");
        WebElement buttonLogin = driver.findElement(AppiumBy.accessibilityId("test-LOGIN"));
        buttonLogin.click();

        //WebElement textError = driver.findElement(AppiumBy.accessibilityId("test-Error message"));
        WebElement textError = driver.findElement(AppiumBy.xpath( "//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]"));
        String actualErrorText = textError.getAttribute("text");

        Assert.assertEquals(actualErrorText, "Username and password do not match any user in this service.");
    }

    @Test(priority=2)
    public void correctEnter() {
        WebElement fieldUserName = driver.findElement(AppiumBy.accessibilityId("test-Username"));
        fieldUserName.sendKeys("standard_user");
        WebElement fieldPassword = driver.findElement(AppiumBy.accessibilityId("test-Password"));
        fieldPassword.sendKeys("secret_sauce");
        WebElement buttonLogin = driver.findElement(AppiumBy.accessibilityId("test-LOGIN"));
        buttonLogin.click();

        WebElement productsPage = driver.findElement(AppiumBy.
                xpath("//android.widget.TextView[@text='PRODUCTS']"));
        String productsTitle = productsPage.getAttribute("text");
        Assert.assertEquals(productsTitle, "PRODUCTS");
    }
*/

    @Parameters({"platformName", "platformVersion", "deviceName"})
    @BeforeTest
    public void beforeTest(String platformName, String platformVersion, String deviceName) throws Exception {
        try {
            props = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);

            URL appUrl = getClass().getClassLoader().getResource(props.getProperty("AndroidAppLocation"));
            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName(platformName)
                    .setPlatformVersion(platformVersion)
                    .setDeviceName(deviceName)
                    .setAutomationName(props.getProperty("AndroidAutomationName"))
                    //.setUdid(props.getProperty("deviceUdid"))
                    //.setUnlockType(props.getProperty("deviceUnlockType"))
                    //.setUnlockKey(props.getProperty("deviceUnlockKey"))
                    //.setAppPackage(props.getProperty("AndroidAppPackage"))
                    //.setAppActivity(props.getProperty("AndroidAppPackage"))
                    .setApp(appUrl)
            ;

            URL url = new URL(props.getProperty("appiumURL"));

            driver = new AndroidDriver(url, options);
            String sessionId = driver.getSessionId().toString();
            //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
