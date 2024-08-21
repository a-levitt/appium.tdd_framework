package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @AndroidFindBy (accessibility = "test-Username")
    private WebElement fieldUserName;
    @AndroidFindBy (accessibility = "test-Password")
    private WebElement fieldPassword;
    @AndroidFindBy (accessibility = "test-LOGIN")
    private WebElement buttonLogin;
    @AndroidFindBy (xpath = "//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]")
    private WebElement textError;

    BaseTest base;

    public LoginPage() {
        base = new BaseTest();
        PageFactory.initElements(new AppiumFieldDecorator(base.getDriver()), this);
    }

    public LoginPage enterUsername(String username) {
        base.sendKeys(fieldUserName, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        base.sendKeys(fieldPassword, password);
        return this;
    }

    public String getErrorText() {
        return base.getAttribute(textError, "text");
    }

    public ProductsPage pressLogin() {
        base.click(buttonLogin);
        return new ProductsPage();
    }
}
