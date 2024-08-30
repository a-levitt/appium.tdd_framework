package com.qa.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsPage extends BasePage{

    public SettingsPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy (accessibility = "test-LOGOUT")
    @iOSXCUITFindBy (id = "test-LOGOUT")
    private WebElement buttonLogout;

    public LoginPage pressLogoutBtn() {
        click(buttonLogout);
        return new LoginPage(driver);
    }
}
