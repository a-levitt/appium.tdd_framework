package com.qa.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductsDetailsPage extends MenuPage {

    public ProductsDetailsPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    @AndroidFindBy (xpath = "//android.widget.TextView[@text=\"Sauce Labs Backpack\"]")
    private WebElement textProductBackpackTitle;
    @AndroidFindBy (xpath = "//android.widget.TextView[@text=\"carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.\"]")
    private WebElement textProductBackpackDescription;

    @AndroidFindBy (accessibility = "test-BACK TO PRODUCTS")
    private WebElement buttonReturnToProducts;

    public String getBackpackTitle() {
        return getText(textProductBackpackTitle);
    }

    public String getBackpackPrice() {
        return getText(textProductBackpackDescription);
    }

    public ProductsPage pressBackToProductsBtn() {
        click(buttonReturnToProducts);
        return new ProductsPage(driver);
    }
}
