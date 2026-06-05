package com.autoex.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private By cartTable = By.id("cart_info_table");
    private By cartRows = By.cssSelector("#cart_info_table tbody tr");
    private By totalPrices = By.cssSelector(".cart_total_price");
    private By proceedToCheckout = By.cssSelector("a[href='/checkout']");
    private By emptyCartMessage = By.cssSelector(".cart_empty p");
    private By deleteLink = By.cssSelector(".cart_quantity_delete");
    private By closeModal = By.cssSelector(".close-modal");
    private By checkoutBtnText = By.xpath("//a[contains(text(), 'Proceed To')]");
    private By modalLogin = By.cssSelector(".modal-body a[href='/login']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void clickProceedToCheckout() {
        try {
            if (driver.findElements(closeModal).size() > 0) {
                click(closeModal);
            }
        } catch (Exception ignored) {}

        click(checkoutBtnText);
    }

    public boolean isCartPageLoaded() {
        return isVisible(cartTable);
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

    public boolean isProductInCart(String productName) {
        List<WebElement> rows = driver.findElements(cartRows);
        return rows.stream().anyMatch(r -> r.getText().contains(productName));
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