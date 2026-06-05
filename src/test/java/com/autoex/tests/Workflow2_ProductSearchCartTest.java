package com.autoex.tests;

import com.autoex.dataproviders.ProductCartDataProvider;
import com.autoex.pages.*;
import com.autoex.utils.*;
import com.aventstack.extentreports.ExtentTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Workflow2_ProductSearchCartTest extends BaseTest {
    private HomePage home;
    private ProductsPage products;
    private ProductDetailPage detail;
    private CartPage cart;

    @BeforeClass(alwaysRun = true)
    public void setupPages() {
        home = new HomePage(DriverManager.getDriver());
        products = new ProductsPage(DriverManager.getDriver());
        detail = new ProductDetailPage(DriverManager.getDriver());
        cart = new CartPage(DriverManager.getDriver());
    }

    @Test(groups = { "products", "positive" }, description = "Products page loads and displays items")
    public void testProductsPageLoads() {
        ExtentTest test = ExtentManager.getTest();
        home.navigateTo();
        home.clickProducts();
        products.waitForPage();

        int count = products.getProductCount();
        Assert.assertTrue(count > 0, "Products page should display at least one product");
        test.pass("Products page loaded with " + count + " products");
    }

    @Test(dataProvider = "searchPositiveData", dataProviderClass = ProductCartDataProvider.class,
          groups = { "products", "positive" }, description = "Search with valid keyword")
    public void testSearchReturnsResults(String keyword, int expectedMin) {
        ExtentTest test = ExtentManager.getTest();
        home.navigateTo();
        home.clickProducts();
        products.searchProduct(keyword);

        Assert.assertTrue(products.hasSearchResults(), "Search for '" + keyword + "' should return results");
        int count = products.getProductCount();
        Assert.assertTrue(count >= expectedMin, "Expected >= " + expectedMin + " results");
        test.pass("Search '" + keyword + "' returned " + count + " product(s)");
    }

    @Test(groups = { "products", "positive" }, description = "Product detail page loads")
    public void testProductDetailPageLoads() {
        ExtentTest test = ExtentManager.getTest();
        home.navigateTo();
        home.clickProducts();
        products.clickViewFirstProduct();

        Assert.assertTrue(detail.isProductPageLoaded(), "Product detail page should load");
        Assert.assertFalse(detail.getProductName().isEmpty(), "Name should not be empty");
        test.pass("Product detail loaded: " + detail.getProductName());
    }


    @Test(groups = { "cart", "negative" }, description = "Checkout without login")
    public void testCheckoutWithoutLogin() {
        ExtentTest test = ExtentManager.getTest();
        home.navigateTo();
        home.clickProducts();
        products.addFirstProductToCart();
        products.goToCartFromModal();
        cart.clickProceedToCheckout();
        boolean promptsLogin = driver.getCurrentUrl().contains("/login")
            || driver.getPageSource().contains("Register / Login");

        Assert.assertTrue(promptsLogin, "Guest user should be prompted to login");
        test.pass("Guest checkout correctly redirected to login");
    }

    @Test(dataProvider = "searchEdgeData", dataProviderClass = ProductCartDataProvider.class,
          groups = { "products", "edge" }, description = "Search handles edge-case inputs")
    public void testSearchEdgeCases(String keyword, String description) {
        home.navigateTo();
        home.clickProducts();
        products.searchProduct(keyword);

        Assert.assertFalse(driver.getPageSource().contains("Uncaught"), "No JS crash for: " + description);
        ExtentManager.getTest().pass("Edge search handled safely: " + description);
    }
}

