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
    protected static ThreadLocal<String> deviceName = new ThreadLocal<String>();
    TestUtils utils;

    public static AppiumDriver getDriver() {
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

    public static String getPlatform() {
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

    public String getDeviceName() {
        return deviceName.get();
    }

    public void setDeviceName(String deviceName2) {
        deviceName.set(deviceName2);
    }

    // "chromeDevicePort" for webView Android "webkitDebugProxyPort" for webView iOS
    //  @Optional("iOSOnly")int wdaLocalPort
    @Parameters({"emulator", "platformName", "platformVersion", "udid", "deviceName", "systemPort", "wdaLocalPort"})
    @BeforeTest
    public void beforeTest(@Optional("androidOnly")String emulator, String platformName, String platformVersion, String deviceName, String udid, @Optional("androidOnly")int systemPort) throws Exception {
        InputStream inputStream = null;
        InputStream stringsis = null;
        utils = new TestUtils();
        setPlatform(platformName);
        setDeviceName(deviceName);
        URL url;
        setDateTime(utils.dateTime());
        Properties props = new Properties();
        AppiumDriver driver;
        try {
            props = new Properties();
            String propFileName = "config.properties";
            String xmlFilename = "strings/strings.xml";
            url = new URL(props.getProperty("appiumURL") + "4723");

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);
            setProps(props);

            stringsis = getClass().getClassLoader().getResourceAsStream(xmlFilename);

            setStrings(utils.parseStringXML(stringsis));

            switch (platformName) {
                case "Android":
                    String AndroidAppPath =
                            System.getProperty("user.dir") + File.separator + "src" + File.separator +
                                    "main" + File.separator + "resources" + File.separator +
                                    "app" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";

                    UiAutomator2Options options = new UiAutomator2Options()
                            .setPlatformName(platformName)
                            .setDeviceName(deviceName)
                            //.setPlatformVersion(platformVersion)
                            //.setUdid(udid)
                    ;
                    options.setAutomationName(props.getProperty("AndroidAutomationName"));
                    if (emulator.equalsIgnoreCase("true")) {
                        options.setPlatformVersion(platformVersion);
                        options.setUnlockType(props.getProperty("deviceUnlockType"));
                        options.setUnlockKey(props.getProperty("deviceUnlockKey"));
                    } else {
                        options.setUdid(udid);
                        options.setUnlockType("pattern");
                        options.setUnlockKey("1532589");
                    }
                    options.setSystemPort(systemPort);

                    options.setAppWaitActivity(props.getProperty("AndroidAppWaitActivity"));
                    options.setAppPackage(props.getProperty("AndroidAppPackage"));
                    options.setAppActivity(props.getProperty("AndroidAppActivity"));
                    options.setApp(AndroidAppPath);

                    driver = new AndroidDriver(url, options);
                    break;

                case "iOS":
                    XCUITestOptions iOSOptions = new XCUITestOptions()
                            .setPlatformName(platformName)
                            .setPlatformVersion(platformVersion)
                            .setDeviceName(deviceName)
                    ;
                    //iOSOptions.setWdaLocalPort(wdaLocalPort);
                    iOSOptions.setAutomationName(props.getProperty("iOSAutomationName"));
                    iOSOptions.setBundleId(props.getProperty("iOSBundleId"));
                    iOSOptions.simpleIsVisibleCheck();

                    String iOSAppPath =
                            System.getProperty("user.dir") + File.separator + "src" + File.separator +
                                    "main" + File.separator + "resources" + File.separator +
                                    "app" + File.separator + "iOS.Simulator.SauceLabs.Mobile.Sample.app.2.7.1.app";
                    iOSOptions.setApp(iOSAppPath);

                    driver = new IOSDriver(url, iOSOptions);
                    break;

                default:
                    throw new Exception("Invalid platform :" + platformName);

            }
            setDriver(driver);
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
        ((CanRecordScreen) getDriver()).startRecordingScreen();
    }

    @AfterMethod
    public synchronized void afterMethod(ITestResult result) {
        String media = ((CanRecordScreen) getDriver()).stopRecordingScreen();

        if (result.getStatus() == 2) {
            Map<String, String> params = new HashMap<>();
            params = result.getTestContext().getCurrentXmlTest().getAllParameters();
            String dir = "Videos" + File.separator +  params.get("platformName") + "_" +
                    params.get("platformVersion") + "_" + params.get("deviceName") + File.separator +
                    dateTime + File.separator + result.getTestClass().getRealClass().getSimpleName();
            File videoDir = new File(dir);

            synchronized(videoDir) {
                if (!videoDir.exists()) {
                    videoDir.mkdirs();
                }
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
        getDriver().quit();
    }

    public void closeApp() {
        switch (getPlatform()) {
            case "Android":
                ((InteractsWithApps) getDriver()).terminateApp(getProps().getProperty("AndroidAppPackage"));
                break;
            case "iOS":
                ((InteractsWithApps) getDriver()).terminateApp(getProps().getProperty("iOSBundleId"));
                break;
        }
    }

    public void launchApp() {
        switch (getPlatform()) {
            case "Android":
                ((InteractsWithApps) getDriver()).activateApp(getProps().getProperty("AndroidAppPackage"));
                break;
            case "iOS":
                ((InteractsWithApps) getDriver()).activateApp(getProps().getProperty("iOSBundleId"));
                break;
        }
    }
}
