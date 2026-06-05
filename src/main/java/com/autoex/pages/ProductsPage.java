package com.autoex.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductsPage extends BasePage {

    private By searchInput = By.id("search_product");
    private By searchButton = By.id("submit_search");
    private By productNames = By.cssSelector(".productinfo h2");
    private By productCards = By.cssSelector(".product-image-wrapper");
    private By pageTitle = By.cssSelector(".features_items h2.title");
    private By searchResultTitle = By.cssSelector(".searched-products h2");
    private By viewProductLinks = By.cssSelector("a[href^='/product_details']");
    private By addToCartButtons = By.cssSelector(".productinfo a.add-to-cart");
    private By modalContinueButton = By.cssSelector(".modal-footer a.btn");
    private By modalViewCartButton = By.cssSelector(".modal-body a[href='/view_cart']");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public void waitForPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
    }

    public void searchProduct(String keyword) {
        type(searchInput, keyword);
        click(searchButton);
        sleep(1500);
    }

    public int getProductCount() {
        return driver.findElements(productCards).size();
    }

    public boolean hasSearchResults() {
        try {
            return !driver.findElements(productCards).isEmpty();
        } catch (Exception e) { return false; }
    }

    public boolean isProductDisplayed(String productName) {
        List<WebElement> names = driver.findElements(productNames);
        return names.stream()
            .anyMatch(el -> el.getText().toLowerCase().contains(productName.toLowerCase()));
    }

    public void clickViewFirstProduct() {
        List<WebElement> viewLinks = driver.findElements(viewProductLinks);
        if (!viewLinks.isEmpty()) click(viewLinks.get(0));
    }

    public void addFirstProductToCart() {
        List<WebElement> addButtons = driver.findElements(addToCartButtons);
        if (!addButtons.isEmpty()) {
            scrollToElement(addButtons.get(0));
            jsClick(addButtons.get(0)); 
            sleep(1500);
        }
    }

    public void addProductToCartByIndex(int idx) {
        List<WebElement> addButtons = driver.findElements(addToCartButtons);
        if (idx < addButtons.size()) {
            scrollToElement(addButtons.get(idx));
            jsClick(addButtons.get(idx));
            sleep(1500);
        }
    }

    public void dismissCartModal() {
        try {
            click(modalContinueButton);
            sleep(500);
        } catch (Exception ignored) {}
    }

    public void goToCartFromModal() {
        try {
            click(modalViewCartButton);
            sleep(1000);
        } catch (Exception ignored) {}
    }

    public String getFirstProductName() {
        List<WebElement> names = driver.findElements(productNames);
        if (!names.isEmpty()) return names.get(0).getText();
        return "";
    }

    public boolean isSearchResultTitleVisible() {
        return isVisible(searchResultTitle);
    }

    public String getPageTitle() {
        return isVisible(pageTitle) ? getText(pageTitle) : driver.getTitle();
    }
}