package com.autoex.tests;

import com.autoex.dataproviders.RegistrationDataProvider;
import com.autoex.pages.*;
import com.autoex.utils.ExtentManager;
import com.aventstack.extentreports.ExtentTest;
import com.autoex.utils.DriverManager;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Workflow1_RegistrationLoginTest extends BaseTest {

    private HomePage home;
    private LoginPage loginPage;
    private RegisterPage reg;

    @BeforeMethod(alwaysRun = true) 
    public void setupPages() {
        this.home = new HomePage(DriverManager.getDriver());
        this.loginPage = new LoginPage(DriverManager.getDriver());
        this.reg = new RegisterPage(DriverManager.getDriver());
    }

    @Test(
        dataProvider = "registrationPositiveData",
        dataProviderClass = RegistrationDataProvider.class,
        groups = { "registration", "positive" },
        description = "Register a new account with valid details and verify success"
    )
    public void testRegistrationSuccess(String name, String email, String password,
                                        String day, String month, String year,
                                        String firstName, String lastName, String address,
                                        String state, String city, String zip, String mobile) {
        
        ExtentTest test = ExtentManager.getTest();
        test.info("Registering: " + name + " | " + email);

        home.navigateTo();
        home.clickSignupLogin();
        loginPage.fillSignupForm(name, email);

        reg.waitForPage();
        reg.selectGender("Mr");
        reg.fillAccountInfo(password, day, month, year);
        reg.fillAddressInfo(firstName, lastName, address, state, city, zip, mobile);
        reg.clickCreateAccount();

        Assert.assertTrue(reg.isAccountCreated(), "Account Created page should appear");
        test.pass("Account created for: " + email);

        reg.clickContinue();
        Assert.assertTrue(home.isLogoutVisible(), "Logout link should be visible");
        
        home.deleteAccount(); 
    }
}