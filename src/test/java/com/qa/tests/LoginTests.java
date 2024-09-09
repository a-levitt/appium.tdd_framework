package com.qa.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Map;

public class LoginTests extends BaseTest {

    LoginPage loginPage;
    ProductsPage productsPage;
    Map<String, Map<String, Object>> map = null;

    @BeforeClass
    public void beforeClass() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String filePath = getClass().getClassLoader().getResource("data/loginUsers.json").getFile();
        try {
            map = objectMapper.readValue(new File(filePath), new TypeReference<Map<String, Map<String, Object>>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        closeApp();
        launchApp();
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        loginPage = new LoginPage();
        System.out.println("\n ***** starting test: " + method.getName() + "***** \n");
    }

    @Test
    public void testUserNameNegative() {
        // Fetching the inner map for the key "invalidUser"
        Map<String, Object> invalidUserMap = map.get("invalidUser");

        // Extracting the username and password
        String username = (String) invalidUserMap.get("username");
        log.info("Using username: " + username);
        String password =(String) invalidUserMap.get("password");
        log.info("Using password: " + password);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.pressLogin();

        checkErrorDisplaying(loginPage);
    }

    @Test
    public void testPasswordNegative() {
        // Fetching the inner map for the key "invalidPassword"
        Map<String, Object> invalidPasswordMap = map.get("invalidPassword");

        // Extracting the username and password
        String username = (String) invalidPasswordMap.get("username");
        log.info("Using username: " + username);
        String password =(String) invalidPasswordMap.get("password");
        log.info("Using password: " + password);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.pressLogin();

        checkErrorDisplaying(loginPage);
    }

    @Test (priority = 2)
    public void testLoginPositive() {
        // Fetching the inner map for the key "validUserData"
        Map<String, Object> validUserDataMap = map.get("validUserData");

        // Extracting the username and password
        String username = (String) validUserDataMap.get("username");
        log.info("Using username: " + username);
        String password =(String) validUserDataMap.get("password");
        log.info("Using password: " + password);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        productsPage = loginPage.pressLogin();

        String actualProductsTitle = productsPage.getTitle();
        String expectedProductsTitle = getStrings().get("products_title");
        Assert.assertEquals(actualProductsTitle, expectedProductsTitle);
    }

    @AfterMethod
    public void afterMethod() {

    }

    @AfterClass
    public void afterClass() {

    }

    public void checkErrorDisplaying(LoginPage page) {
        String actualErrorText = page.getErrorText();
        String expectedErrorText = getStrings().get("err_invalid_user_data");
        Assert.assertEquals(actualErrorText, expectedErrorText);
    }
}
