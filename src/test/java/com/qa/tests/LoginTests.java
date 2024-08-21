package com.qa.tests;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;
import com.qa.utils.TestUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class LoginTests {

    LoginPage loginPage;
    ProductsPage productsPage;
    BaseTest base;

    @Parameters({"platformName", "platformVersion", "deviceName"})
    @BeforeClass
    public void beforeClass(String platformName, String platformVersion, String deviceName) throws Exception {
        base = new BaseTest();
        base.initializeDriver(platformName, platformVersion,deviceName);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        loginPage = new LoginPage();
        System.out.println("\n ***** starting test: " + method.getName() + "***** \n");
    }

    @Test
    public void testUserNameNegative() {
        loginPage.enterUsername("invalidUserName");
        loginPage.enterPassword(TestUtils.PASSWORD);
        loginPage.pressLogin();

        checkErrorDisplaying(loginPage);
    }

    @Test
    public void testPasswordNegative() {
        loginPage.enterUsername(TestUtils.USER_NAME);
        loginPage.enterPassword("invalidPassword");
        loginPage.pressLogin();

        checkErrorDisplaying(loginPage);
    }

    @Test (priority = 2)
    public void testLoginPositive() {
        loginPage.enterUsername(TestUtils.USER_NAME);
        loginPage.enterPassword(TestUtils.PASSWORD);
        productsPage = loginPage.pressLogin();

        String actualProductsTitle = productsPage.getTitle();
        String expectedProductsTitle = "PRODUCTS";
        Assert.assertEquals(actualProductsTitle, expectedProductsTitle);
    }

    @AfterMethod
    public void afterMethod() {

    }

    @AfterClass
    public void afterClass() {
        base.quitDriver();
    }

    public void checkErrorDisplaying(LoginPage page) {
        String actualErrorText = page.getErrorText();
        String expectedErrorText = "Username and password do not match any user in this service.";
        Assert.assertEquals(actualErrorText, expectedErrorText);
    }
}
