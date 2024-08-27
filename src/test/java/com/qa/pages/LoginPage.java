package com.qa.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    public LoginPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy (accessibility = "test-Username")
    @iOSXCUITFindBy(id = "test-Username")
    private WebElement fieldUserName;
    @AndroidFindBy (accessibility = "test-Password")
    @iOSXCUITFindBy(id = "test-Password")
    private WebElement fieldPassword;
    @AndroidFindBy (accessibility = "test-LOGIN")
    @iOSXCUITFindBy(id = "test-LOGIN")
    private WebElement buttonLogin;
    @AndroidFindBy (xpath = "//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]")
    @iOSXCUITFindBy (xpath = "//XCUIElementTypeStaticText[@label=\"Username and password do not match any user in this service.\"]")
    private WebElement textError;

    public LoginPage enterUsername(String username) {
        clear(fieldUserName);
        sendKeys(fieldUserName, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        clear(fieldPassword);
        sendKeys(fieldPassword, password);
        return this;
    }

    public String getErrorText() {
        return getText(textError);
    }

    public ProductsPage pressLogin() {
        click(buttonLogin);
        return new ProductsPage(driver);
    }

    public ProductsPage logIn(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return pressLogin();
    }
}
