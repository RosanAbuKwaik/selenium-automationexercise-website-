package com.autoex.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
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


    
    
    public void waitForPage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search_product")));
        } catch (Exception e) {
            System.out.println("Search bar not found, but proceeding...");
        }
    }
   
    
    
    public void addFirstProductToCart() {
        driver.findElement(By.cssSelector(".add-to-cart")).click();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Continue Shopping']")));
            continueBtn.click();
        } catch (Exception e) {
        }
    }
    
    public String getFirstProductName() {
        return driver.findElement(By.xpath("(//div[@class='productinfo text-center']/p)[1]")).getText();
    }
    
    

    public String getFirstProductPrice() {
        return driver.findElement(By.cssSelector(".productinfo h2")).getText(); 
    }
    
    
    public void addProductToCartByIndex(int idx) {
        List<WebElement> addButtons = driver.findElements(addToCartButtons);
        if (idx < addButtons.size()) {
            scrollToElement(addButtons.get(idx));
            jsClick(addButtons.get(idx));
            wait.until(ExpectedConditions.elementToBeClickable(modalContinueButton));
        }
    }

    public void dismissCartModal() {
        try {
            click(modalContinueButton);
            wait.until(ExpectedConditions.invisibilityOf(driver.findElement(modalContinueButton)));
        } catch (Exception ignored) {}
    }
    
    public void searchProduct(String keyword) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(keyword);
        click(searchButton);
        
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(searchResultTitle),
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".features_items")) // أو أي عنصر يدل أن الصفحة حدثت
            ));
        } catch (Exception e) {
        }
    }

    public void clickViewFirstProduct() {
        dismissCartModal(); 
        
        List<WebElement> viewLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(viewProductLinks));
        if (!viewLinks.isEmpty()) {
            scrollToElement(viewLinks.get(0));
            jsClick(viewLinks.get(0));
        }
    }
    

    public void goToCartFromModal() {
        try {
            click(modalViewCartButton);
        } catch (Exception ignored) {}
    }

    

    public boolean isSearchResultTitleVisible() {
        return isVisible(searchResultTitle);
    }

    public String getPageTitle() {
        return isVisible(pageTitle) ? getText(pageTitle) : driver.getTitle();
    }
}