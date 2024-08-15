package qa.mobile;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class TestApp {
    @Test
    public void f() {

    }

    @BeforeClass
    public void beforeClass() throws MalformedURLException {
                UiAutomator2Options options = new UiAutomator2Options()
                        .setPlatformName("Android")
                        .setAutomationName("UiAutomator2")
                        //.setUdid("emulator-5554")
                        .setAvdLaunchTimeout(Duration.ofSeconds(60))
                        .setAvd("Pixel_8_virt")
                        .setUnlockType("pin")
                        .setUnlockKey("1111")
                        .setAppPackage("com.swaglabsmobileapp")
                        .setAppActivity("com.swaglabsmobileapp.SplashActivity")
                        .setApp("D:\\Appium\\Android.SauceLabs.Mobile.Sample.app.2.7.1.apk")
                        ;

                URL url = new URL("http://0.0.0.0:4723");

                AndroidDriver driver = new AndroidDriver(url, options);
                String sessionId = driver.getSessionId().toString();

    }

    @AfterClass
    public void afterClass() {

    }
}
