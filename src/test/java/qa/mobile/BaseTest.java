package qa.mobile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {

    // remove from BaseTest class PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    // in every page constructor:
    //      public LoginPage() { PageFactory.initElements(new AppiumFieldDecorator(driver), this); }

    protected AppiumDriver driver;
    protected Properties props;
    InputStream inputStream;

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

    @BeforeTest
    public void beforeTest() throws Exception {
        try {
            props = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);

            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName("Android")
                    .setAutomationName(props.getProperty("AndroidAutomationName"))
                    .setUdid(props.getProperty("deviceUdid"))
                    //.setAvd("Pixel_8_virt")
                    //.setAvdLaunchTimeout(Duration.ofSeconds(120))
                    .setUnlockType(props.getProperty("deviceUnlockType"))
                    .setUnlockKey(props.getProperty("deviceUnlockKey"))
                    .setAppPackage(props.getProperty("AndroidAppPackage"))
                    .setAppActivity(props.getProperty("AndroidAppPackage"))
                    .setApp(props.getProperty("AndroidAppLocation"))
            ;

            URL url = new URL(props.getProperty("appiumURL"));

            driver = new AndroidDriver(url, options);
            String sessionId = driver.getSessionId().toString();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @AfterTest
    public void afterClass() {
        driver.quit();
    }
}
