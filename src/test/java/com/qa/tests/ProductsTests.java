package com.qa.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductDetailsPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingsPage;
import com.qa.utils.DeepLink;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class ProductsTests extends BaseTest {

    LoginPage loginPage;
    ProductsPage productsPage;
    SettingsPage settingsPage;
    ProductDetailsPage productDetailsPage;
    Map<String, Map<String, Object>> map = null;

    @BeforeClass
    public void beforeClass() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String filePath = getClass().getClassLoader().getResource("data/loginUsers.json").getFile();
        //System.out.println(filePath);
        try {
            map = objectMapper.readValue(new File(filePath), new TypeReference<Map<String, Map<String, Object>>>() {
            });
            //System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        //closeApp();
        //launchApp();
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        loginPage = new LoginPage();
        System.out.println("\n ***** starting test: " + method.getName() + "***** \n");

        /*// Fetching the inner map for the key "validUserData"
        Map<String, Object> validUserDataMap = map.get("validUserData");

        // Extracting the username and password
        String username = (String) validUserDataMap.get("username");
        log.info("Using username: " + username);
        String password =(String) validUserDataMap.get("password");
        log.info("Using password: " + password);

        productsPage = loginPage.logIn(username, password);*/

        DeepLink.OpenAppWith("swaglabs://swag-overview/0,1");
        productsPage = new ProductsPage();
    }

    @Test
    public void testProductOnProductsPagePositive() {

        SoftAssert sa = new SoftAssert();

        String backpackTitle = productsPage.getBackpackTitle();
        sa.assertEquals(backpackTitle, getStrings().get("products_backpack_title"));

        String backpackPrice = productsPage.getBackpackPrice();
        sa.assertEquals(backpackPrice, getStrings().get("products_backpack_price"));

        sa.assertAll();
    }

    @Test (priority = 2)
    public void testProductDetailsOnBackpackPagePositive() {

        SoftAssert sa = new SoftAssert();

        productDetailsPage =  productsPage.pressBackpackProduct();

        String backpackProductTitle = productDetailsPage.getBackpackTitle();
        sa.assertEquals(backpackProductTitle, getStrings().get("product_details_backpack_title"));

        String backpackProductDescription = productDetailsPage.getBackpackDescription();
        sa.assertEquals(backpackProductDescription, getStrings().get("product_details_backpack_description"));

        //productDetailsPage.scrollToBackpackPrice();
        //String backpackProductPrice = productDetailsPage.getBackpackPrice();

        String backpackProductPrice = productDetailsPage.scrollToBackpackPriceAndGet();
        sa.assertEquals(backpackProductPrice, getStrings().get("product_details_backpack_price"));

        productsPage = productDetailsPage.pressBackToProductsBtn();

        sa.assertAll();
    }

    @AfterMethod
    public void afterMethod() {
        //settingsPage = productsPage.pressSettingsBtn();
        //loginPage = settingsPage.pressLogoutBtn();
        //closeApp();
        //launchApp();
    }

    @AfterClass
    public void afterClass() {

    }
}
