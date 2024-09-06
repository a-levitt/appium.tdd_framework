package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsPage extends BasePage{

    public SettingsPage() {
        driver = BaseTest.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy (accessibility = "test-LOGOUT")
    @iOSXCUITFindBy (id = "test-LOGOUT")
    private WebElement buttonLogout;

    public LoginPage pressLogoutBtn() {
        click(buttonLogout);
        return new LoginPage();
    }
}
