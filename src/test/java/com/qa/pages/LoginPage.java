package com.qa.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    public LoginPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy (accessibility = "test-Username")
    private WebElement fieldUserName;
    @AndroidFindBy (accessibility = "test-Password")
    private WebElement fieldPassword;
    @AndroidFindBy (accessibility = "test-LOGIN")
    private WebElement buttonLogin;
    @AndroidFindBy (xpath = "//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]")
    private WebElement textError;

    public LoginPage enterUsername(String username) {
        sendKeys(fieldUserName, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        sendKeys(fieldPassword, password);
        return this;
    }

    public ProductsPage pressLogin() {
        click(buttonLogin);
        return new ProductsPage(driver);
    }
}
