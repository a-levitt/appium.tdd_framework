package com.qa.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage extends BasePage {

    public ProductsPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy (xpath = "//android.widget.TextView[@text='PRODUCTS']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label=\"PRODUCTS\"]")
    private WebElement textProductsTitle;

    public String getTitle() {
        return getText(textProductsTitle);
    }
}
