package com.autoex.tests;

import com.autoex.dataproviders.CheckoutDataProvider;
import com.autoex.pages.*;
import com.autoex.utils.DriverManager;
import com.autoex.utils.ExtentManager;
import com.aventstack.extentreports.ExtentTest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Workflow3_CheckoutTest extends BaseTest {

    private static final String TEST_EMAIL = "test@test.com";
    private static final String TEST_PASSWORD = "test123";
        private HomePage home;
    private LoginPage loginPage;
    private ProductsPage products;
    private CartPage cart;
    private CheckoutPage checkout;
    private ProductDetailPage detail;
    @BeforeClass(alwaysRun = true)
    public void setupPages() {
        home = new HomePage(driver); 
        loginPage = new LoginPage(driver);
        products = new ProductsPage(driver);
        cart = new CartPage(driver);
        checkout = new CheckoutPage(driver);
    }
    
    


    private boolean loginAndAddToCart() {
        home.navigateTo();
        home.clickSignupLogin();
        loginPage.login(TEST_EMAIL, TEST_PASSWORD);

        if (!home.isLogoutVisible()) return false;

        home.clickProducts();
        products.waitForPage();
        products.addFirstProductToCart();
        products.goToCartFromModal();
        return true;
    }

    @Test(dataProvider = "checkoutPositiveData", dataProviderClass = CheckoutDataProvider.class,
          groups = { "checkout", "positive" })
    public void testFullCheckoutSuccess(String cardName, String cardNumber, String cvc,
                                        String expiryMonth, String expiryYear, String comment) {
        ExtentTest test = ExtentManager.getTest();
        
        if (!loginAndAddToCart()) { test.skip("Login failed"); return; }

        cart.clickProceedToCheckout(); 
        
        Assert.assertTrue(checkout.isCheckoutPageLoaded());
        if (!comment.isEmpty()) checkout.addComment(comment);
        
        checkout.clickPlaceOrder();
        checkout.fillPaymentDetails(cardName, cardNumber, cvc, expiryMonth, expiryYear);
        checkout.clickPayAndConfirm();

        Assert.assertTrue(checkout.isOrderPlaced());
        test.pass("Order placed successfully");
    }


    @Test(groups = { "checkout", "negative" }, description = "Guest cannot reach checkout")
    public void testGuestCheckoutBlocked() {
        home.navigateTo();
        home.clickProducts();
        products.addFirstProductToCart();
        products.goToCartFromModal();
        cart.clickProceedToCheckout();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginLinkInModal = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='/login']")));
        Assert.assertTrue(loginLinkInModal.isDisplayed(), "Guest must be prompted to login/register");
        
        ExtentManager.getTest().pass("Guest checkout blocked correctly — login modal appeared");
    }
    
    
    
}