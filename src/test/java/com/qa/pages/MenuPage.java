package com.qa.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MenuPage extends BasePage{

    public MenuPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Menu\"]/XCUIElementTypeOther")
    private WebElement buttonOpenSettings;

    public SettingsPage pressSettingsBtn() {
        click(buttonOpenSettings);
        return new SettingsPage(driver);
    }
}
