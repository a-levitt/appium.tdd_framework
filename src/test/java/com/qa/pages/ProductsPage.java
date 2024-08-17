package com.qa.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage extends BasePage {

    public ProductsPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy (xpath = "//android.widget.TextView[@text='PRODUCTS']")
    private WebElement textProductsTitle;

    public String getTitle() {
        return getAttribute(textProductsTitle, "text");
    }
}
