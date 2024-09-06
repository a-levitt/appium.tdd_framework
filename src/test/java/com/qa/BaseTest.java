package com.qa;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BaseTest {

    protected static ThreadLocal<AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
    protected static ThreadLocal<Properties> props = new ThreadLocal<Properties>();
    protected static ThreadLocal<HashMap<String, String>>  strings = new ThreadLocal<HashMap<String, String>>();
    protected static ThreadLocal<String> dateTime = new ThreadLocal<String>();
    protected static ThreadLocal<String> platform = new ThreadLocal<String>();
    TestUtils utils;

    public AppiumDriver getDriver() {
        return driver.get();
    }

    public void setDriver(AppiumDriver driver2) {
        driver.set(driver2);
    }

    public Properties getProps() {
        return props.get();
    }

    public void setProps(Properties props2) {
        props.set(props2);
    }

    public HashMap<String, String> getStrings() {
        return strings.get();
    }

    public void setStrings(HashMap<String, String> strings2) {
        strings.set(strings2);
    }

    public String getPlatform() {
        return platform.get();
    }

    public void setPlatform(String platform2) {
        platform.set(platform2);
    }

    public String getDateTime() {
        return dateTime.get();
    }

    public void setDateTime(String dateTime2) {
        dateTime.set(dateTime2);
    }

    @Parameters({"emulator", "platformName", "platformVersion", "udid", "deviceName"})
    @BeforeTest
    public void beforeTest(String emulator, String platformName, String platformVersion, String deviceName, String udid) throws Exception {
        InputStream inputStream;
        InputStream stringsis;
        utils = new TestUtils();
        platform = platformName;
        URL url;
        dateTime = utils.getDateTime();
        try {
            props = new Properties();
            String propFileName = "config.properties";
            String xmlFilename = "strings/strings.xml";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);

            stringsis = getClass().getClassLoader().getResourceAsStream(xmlFilename);

            strings= utils.parseStringXML(stringsis);

            switch (platformName) {
                case "Android":
                    String AndroidAppPath =
                            System.getProperty("user.dir") + File.separator + "src" + File.separator +
                                    "main" + File.separator + "resources" + File.separator +
                                    "app" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";

                    UiAutomator2Options options = new UiAutomator2Options()
                            .setPlatformName(platformName)
                            .setDeviceName(deviceName)
                    ;
                    options.setAutomationName(props.getProperty("AndroidAutomationName"));
                    if (emulator.equalsIgnoreCase("true")) {
                        options.setPlatformVersion(platformVersion);
                        options.setUnlockType(props.getProperty("deviceUnlockType"));
                        options.setUnlockKey(props.getProperty("deviceUnlockKey"));
                    } else {
                        options.setUdid(udid);
                    }

                    options.setAppWaitActivity(props.getProperty("AndroidAppWaitActivity"));
                    options.setAppPackage(props.getProperty("AndroidAppPackage"));
                    options.setAppActivity(props.getProperty("AndroidAppActivity"));
                    options.setApp(AndroidAppPath);

                    url = new URL(props.getProperty("appiumURL"));

                    driver = new AndroidDriver(url, options);
                    break;

                case "iOS":
                    XCUITestOptions iOSOptions = new XCUITestOptions()
                            .setPlatformName(platformName)
                            .setPlatformVersion(platformVersion)
                            .setDeviceName(deviceName)
                    ;
                    iOSOptions.setAutomationName(props.getProperty("iOSAutomationName"));
                    iOSOptions.setBundleId(props.getProperty("iOSBundleId"));
                    iOSOptions.simpleIsVisibleCheck();

                    String iOSAppPath =
                            System.getProperty("user.dir") + File.separator + "src" + File.separator +
                                    "main" + File.separator + "resources" + File.separator +
                                    "app" + File.separator + "iOS.Simulator.SauceLabs.Mobile.Sample.app.2.7.1.app";
                    iOSOptions.setApp(iOSAppPath);

                    url = new URL(props.getProperty("appiumURL"));

                    driver = new IOSDriver(url, iOSOptions);
                    break;

                default:
                    throw new Exception("Invalid platform :" + platformName);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (stringsis != null) {
                stringsis.close();
            }
        }
    }

    @BeforeMethod
    public void beforeMethod() {
        ((CanRecordScreen) driver).startRecordingScreen();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        String media = ((CanRecordScreen) driver).stopRecordingScreen();

        if (result.getStatus() == 2) {
            Map<String, String> params = new HashMap<>();
            params = result.getTestContext().getCurrentXmlTest().getAllParameters();
            String dir = "Videos" + File.separator +  params.get("platformName") + "_" +
                    params.get("platformVersion") + "_" + params.get("deviceName") + File.separator +
                    dateTime + File.separator + result.getTestClass().getRealClass().getSimpleName();
            File videoDir = new File(dir);
            if (!videoDir.exists()) {
                videoDir.mkdirs();
            }
            try {
                FileOutputStream stream = new FileOutputStream(videoDir + File.separator +
                        result.getName() + ".mp4");
                stream.write(Base64.getDecoder().decode(media));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }

    public void closeApp() {
        switch (platform) {
            case "Android":
                ((InteractsWithApps) driver).terminateApp(props.getProperty("AndroidAppPackage"));
                break;
            case "iOS":
                ((InteractsWithApps) driver).terminateApp(props.getProperty("iOSBundleId"));
                break;
        }
    }

    public void launchApp() {
        switch (platform) {
            case "Android":
                ((InteractsWithApps) driver).activateApp(props.getProperty("AndroidAppPackage"));
                break;
            case "iOS":
                ((InteractsWithApps) driver).activateApp(props.getProperty("iOSBundleId"));
                break;
        }
    }
}
