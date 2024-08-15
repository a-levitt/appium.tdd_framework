package payload;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverInitialization {
    public static AppiumDriver initializeDriver() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                //.setAvd("Pixel_8_virt")
                //.setAvdLaunchTimeout(Duration.ofSeconds(180))
                .setUdid("emulator-5554")
                .setAppPackage("io.appium.android.apis")
                .setAppActivity("io.appium.android.apis.ApiDemos")
                //.setApp("D:\\Appium\\course_proj1\\ApiDemos-debug.apk")
                ;

        URL url = new URL("http://0.0.0.0:4723");

        return new AndroidDriver(url, options);
    }
}