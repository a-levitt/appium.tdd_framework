package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage {

    @AndroidFindBy (xpath = "//android.widget.TextView[@text='PRODUCTS']")
    private WebElement textProductsTitle;

    BaseTest base;

    public ProductsPage() {
        base = new BaseTest();
        PageFactory.initElements(new AppiumFieldDecorator(base.getDriver()), this);
    }

    public String getTitle() {
        return base.getAttribute(textProductsTitle, "text");
    }
}
