package com.autoex.pages;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
public class CartPage extends BasePage {

    private By cartTable = By.id("cart_info_table");
    private By cartRows = By.cssSelector("#cart_info_table tbody tr");
    private By emptyCartMessage = By.cssSelector(".cart_empty p");
    private By deleteLink = By.cssSelector(".cart_quantity_delete");
    private By modalLogin = By.cssSelector(".modal-body a[href='/login']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCartPageLoaded() {
        return isVisible(cartTable);
    }
    
    public boolean isProductVisible(String expectedName) {
        String xpath = "//a[text()='" + expectedName + "']";
        return driver.findElements(By.xpath(xpath)).size() > 0;
    }

    public int getCartItemCount() {
        try { 
            return driver.findElements(cartRows).size(); 
        } catch (Exception e) { return 0; }
    }

    public boolean isCartEmpty() {
        try {
            return isVisible(emptyCartMessage) || getCartItemCount() == 0;
        } catch (Exception e) { return true; }
    }

    public void clickProceedToCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(), 'Proceed To Checkout')]")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkoutBtn);
    }
    
    
    
    
    public String getProductNameInCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart_info_table")));
                return driver.findElement(By.cssSelector("td.cart_description h4 a")).getText();
    }
    
    public int getCartCount() {
        return driver.findElements(By.cssSelector("tr[id^='product-']")).size();
    }
    
    
    public boolean isProductInCart(String productName) {
        List<WebElement> rows = driver.findElements(cartRows);
        return rows.stream().anyMatch(r -> r.getText().contains(productName));
    }
    


    public String getProductPriceInCart() {
        return driver.findElement(By.cssSelector("td.cart_price p")).getText();
    }
    
    

    public void removeFirstItem() {
        List<WebElement> deleteLinks = driver.findElements(deleteLink);
        if (!deleteLinks.isEmpty()) {
            deleteLinks.get(0).click();
            sleep(1500);
        }
    }

    public void loginFromCheckoutModal() {
        try {
            click(modalLogin);
            sleep(1000);
        } catch (Exception ignored) {}
    }
}