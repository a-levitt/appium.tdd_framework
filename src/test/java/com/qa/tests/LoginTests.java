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
    public void beforeClass() {
        ObjectMapper objectMapper = new ObjectMapper();
        String filePath = getClass().getClassLoader().getResource("data/loginUsers.json").getFile();
        try {
            map = objectMapper.readValue(new File(filePath), new TypeReference<Map<String, Map<String, Object>>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        loginPage = new LoginPage(driver);
        System.out.println("\n ***** starting test: " + method.getName() + "***** \n");
    }

    @Test
    public void testUserNameNegative() {
        loginPage.enterUsername(map.get("invalidUser").get("username").toString());
        loginPage.enterPassword(map.get("invalidUser").get("password").toString());
        loginPage.pressLogin();

        checkErrorDisplaying(loginPage);
    }

    @Test
    public void testPasswordNegative() {
        loginPage.enterUsername(map.get("invalidPassword").get("username").toString());
        loginPage.enterPassword(map.get("invalidPassword").get("password").toString());
        loginPage.pressLogin();

        checkErrorDisplaying(loginPage);
    }

    @Test (priority = 2)
    public void testLoginPositive() {
        loginPage.enterUsername(map.get("validUserData").get("username").toString());
        loginPage.enterPassword(map.get("validUserData").get("password").toString());
        productsPage = loginPage.pressLogin();

        String actualProductsTitle = productsPage.getTitle();
        String expectedProductsTitle = strings.get("products_title");
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
        String expectedErrorText = strings.get("err_invalid_user_data");
        Assert.assertEquals(actualErrorText, expectedErrorText);
    }
}
