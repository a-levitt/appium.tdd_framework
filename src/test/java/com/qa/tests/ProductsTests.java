package com.qa.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductDetailsPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingsPage;
import org.testng.Assert;
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

        closeApp();
        launchApp();
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        loginPage = new LoginPage(driver);
        System.out.println("\n ***** starting test: " + method.getName() + "***** \n");
    }

    @Test
    public void testProductOnProductsPagePositive() {

        SoftAssert sa = new SoftAssert();

        // Fetching the inner map for the key "validUserData"
        Map<String, Object> validUserDataMap = map.get("validUserData");

        // Extracting the username and password
        String username = (String) validUserDataMap.get("username");
        String password =(String) validUserDataMap.get("password");

        productsPage = loginPage.logIn(username, password);

        String backpackTitle = productsPage.getBackpackTitle();
        sa.assertEquals(backpackTitle, strings.get("products_backpack_title"));

        String backpackPrice = productsPage.getBackpackPrice();
        sa.assertEquals(backpackPrice, strings.get("products_backpack_price"));

        settingsPage = productsPage.pressSettingsBtn();
        loginPage = settingsPage.pressLogoutBtn();

        sa.assertAll();
    }

    @Test
    public void testProductDetailsOnBackpackPagePositive() {

        SoftAssert sa = new SoftAssert();

        // Fetching the inner map for the key "validUserData"
        Map<String, Object> validUserDataMap = map.get("validUserData");

        // Extracting the username and password
        String username = (String) validUserDataMap.get("username");
        String password =(String) validUserDataMap.get("password");

        productsPage = loginPage.logIn(username, password);

        productDetailsPage =  productsPage.pressBackpackProduct();

        String backpackProductTitle = productDetailsPage.getBackpackTitle();
        sa.assertEquals(backpackProductTitle, strings.get("product_details_backpack_title"));

        String backpackProductDescription = productDetailsPage.getBackpackDescription();
        sa.assertEquals(backpackProductDescription, strings.get("product_details_backpack_description"));

        productsPage = productDetailsPage.pressBackToProductsBtn();
        settingsPage = productsPage.pressSettingsBtn();
        loginPage = settingsPage.pressLogoutBtn();

        sa.assertAll();
    }

    @AfterMethod
    public void afterMethod() {

    }

    @AfterClass
    public void afterClass() {

    }
}
