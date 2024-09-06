package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductDetailsPage extends MenuPage {

    public ProductDetailsPage() {
        driver = BaseTest.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    @AndroidFindBy (xpath = "//android.widget.TextView[@text=\"Sauce Labs Backpack\"]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Description\"]/child::XCUIElementTypeStaticText[1]")
    private WebElement textProductBackpackTitle;
    @AndroidFindBy (xpath = "//android.widget.TextView[@text=\"carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.\"]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Description\"]/child::XCUIElementTypeStaticText[2]")
    private WebElement textProductBackpackDescription;

 /*   @AndroidFindBy (accessibility = "test-Price")
    private WebElement textProductBackpackPrice;*/

    @AndroidFindBy (accessibility = "test-BACK TO PRODUCTS")
    @iOSXCUITFindBy (id = "test-BACK TO PRODUCTS")
    private WebElement buttonReturnToProducts;

    public String getBackpackTitle() {
        return getText(textProductBackpackTitle);
    }

    public String getBackpackDescription() {
        return getText(textProductBackpackDescription);
    }

/*    public String getBackpackPrice() {
        return getText(textProductBackpackPrice);
    }

    public ProductDetailsPage scrollToBackpackPrice() {
        scrollToElement();
        return this;
    }*/

    public String scrollToBackpackPriceAndGet() {
        return getText(scrollToElement());
    }

    public ProductsPage pressBackToProductsBtn() {
        click(buttonReturnToProducts);
        return new ProductsPage();
    }
}
