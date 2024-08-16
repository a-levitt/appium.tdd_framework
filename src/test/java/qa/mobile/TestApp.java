package qa.mobile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class TestApp {

    AppiumDriver driver;

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

    @BeforeClass
    public void beforeClass() throws MalformedURLException {
                UiAutomator2Options options = new UiAutomator2Options()
                        .setPlatformName("Android")
                        .setAutomationName("UiAutomator2")
                        .setUdid("emulator-5554")
                        //.setAvd("Pixel_8_virt")
                        //.setAvdLaunchTimeout(Duration.ofSeconds(120))
                        .setUnlockType("pin")
                        .setUnlockKey("1111")
                        .setAppPackage("com.swaglabsmobileapp")
                        .setAppActivity("com.swaglabsmobileapp.SplashActivity")
                        .setApp("D:\\Appium\\Android.SauceLabs.Mobile.Sample.app.2.7.1.apk")
                ;

                URL url = new URL("http://0.0.0.0:4723");

                driver = new AndroidDriver(url, options);
                String sessionId = driver.getSessionId().toString();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
