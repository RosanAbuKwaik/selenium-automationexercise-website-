
package com.autoex.tests;

import com.autoex.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert; // تأكدي من هذا فقط
import org.testng.annotations.Test;
import java.time.Duration;
public class Workflow3_CheckoutTest extends BaseTest {

    private static final String TEST_EMAIL = "rosan123@gmail.com";
    private static final String TEST_PASSWORD = "rosan1234**";



    
    
    @Test(groups = {"checkout"})
    public void testFullCheckoutSuccess() {
        driver.get("https://automationexercise.com/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TEST_EMAIL, TEST_PASSWORD);
        driver.get("https://automationexercise.com/products");
        ProductsPage productsPage = new ProductsPage(driver);
                String expectedName = productsPage.getFirstProductName(); 
        productsPage.addFirstProductToCart();

        driver.get("https://automationexercise.com/view_cart");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
                WebElement cartProductElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='cart_description']/h4/a")));
        String actualName = cartProductElement.getText();
                Assert.assertEquals(actualName, expectedName, "product name not same");

        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Proceed To Checkout')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkoutBtn);
        
        CheckoutPage checkout = new CheckoutPage(driver);
        wait.until(ExpectedConditions.urlContains("checkout"));
        
        checkout.addComment("تجربة دفع مباشرة مع التحقق");
        checkout.clickPlaceOrder();
        
        checkout.fillPaymentDetails("Rawan Doe", "5500005555555559", "456", "06", "2027");
        checkout.clickPayAndConfirm();
        
        Assert.assertTrue(checkout.isOrderPlaced(), "Faild");
    }
    
    
    
    
    
    
    
    
    
    @Test(groups = {"checkout"})
    public void testGuestCheckoutBlocked() {
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        
        ProductsPage products = new ProductsPage(driver);
        CartPage cart = new CartPage(driver);
        
        driver.get("https://automationexercise.com/products");
        products.addFirstProductToCart();
        driver.get("https://automationexercise.com/view_cart");
                cart.clickProceedToCheckout();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement loginLinkInModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//u[contains(text(), 'Register / Login')]")));
        
        Assert.assertTrue(loginLinkInModal.isDisplayed(), "لم تظهر نافذة تسجيل الدخول للضيف!");
    }
}