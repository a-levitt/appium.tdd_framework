package com.qa;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.*;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class BaseTest {

    protected AppiumDriver driver;
    protected Properties props;
    InputStream inputStream;

    @Parameters({"platformName", "platformVersion", "deviceName"})
    @BeforeTest
    public void beforeTest(String platformName, String platformVersion, String deviceName) throws Exception {
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
                    .setApp(appPath)
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

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
