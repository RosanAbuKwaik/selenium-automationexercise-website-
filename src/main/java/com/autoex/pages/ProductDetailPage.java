package com.autoex.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductDetailPage extends BasePage {

    private By productName = By.cssSelector(".product-information h2");
    private By productPrice = By.cssSelector(".product-information span span");
    private By productAvailability = By.cssSelector(".product-information p:nth-child(6)");
    private By quantityField = By.id("quantity");
    private By addToCartButton = By.cssSelector(".cart");
    
    private By modalContinueButton = By.cssSelector(".modal-footer a.btn");
    private By modalViewCartButton = By.cssSelector(".modal-body a[href='/view_cart']");

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductPageLoaded() {
        return isVisible(productName);
    }

    public String getProductName()  { return getText(productName); }
    public String getProductPrice() { return getText(productPrice); }

    public void setQuantity(int qty) {
        type(quantityField, String.valueOf(qty));
    }

    public void clickAddToCart() {
        click(addToCartButton);
        sleep(1500);
    }

    public void dismissModal() {
        try {
            click(modalContinueButton);
        } catch (Exception ignored) {}
    }

    public void goToCartFromModal() {
        try {
            click(modalViewCartButton);
        } catch (Exception ignored) {}
    }

    public boolean isProductAvailable() {
        try {
            return getText(productAvailability).toLowerCase().contains("in stock");
        } catch (Exception e) { return false; }
    }
}