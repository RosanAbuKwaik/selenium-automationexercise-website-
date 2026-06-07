package com.autoex.tests;

import com.autoex.dataproviders.ProductCartDataProvider;
import com.autoex.pages.*;
import com.autoex.utils.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod; 
import org.testng.annotations.Test;

public class Workflow2_ProductSearchCartTest extends BaseTest {
    private HomePage home;
    private ProductsPage products;
    private ProductDetailPage detail;
    private CartPage cart;

    @BeforeMethod(alwaysRun = true)
    public void setupPages() {
        try {
            DriverManager.getDriver().getWindowHandles();
        } catch (Exception e) {
        }
        
        home = new HomePage(DriverManager.getDriver());
        products = new ProductsPage(DriverManager.getDriver());
        detail = new ProductDetailPage(DriverManager.getDriver());
        cart = new CartPage(DriverManager.getDriver());
    }

    private void ensureWeAreOnProductsPage() {
        try {
            if (!DriverManager.getDriver().getCurrentUrl().contains("/products")) {
                home.navigateTo();
                home.clickProducts();
                products.waitForPage();
            }
        } catch (Exception e) {
            home.navigateTo();
            home.clickProducts();
        }
    }

    @Test(groups = { "products", "positive" }, description = "Products page loads and displays items")
    public void testProductsPageLoads() {
        ensureWeAreOnProductsPage();
        int count = products.getProductCount();
        Assert.assertTrue(count > 0, "Products page should display at least one product");
        ExtentManager.getTest().pass("Products page loaded with " + count + " products");
    }

    @Test(dataProvider = "searchPositiveData", dataProviderClass = ProductCartDataProvider.class,
          groups = { "products", "positive" }, description = "Search with valid keyword")
    public void testSearchReturnsResults(String keyword, int expectedMin) {
        ensureWeAreOnProductsPage();
        products.searchProduct(keyword);
        Assert.assertTrue(products.hasSearchResults(), "Search for '" + keyword + "' should return results");
        int count = products.getProductCount();
        Assert.assertTrue(count >= expectedMin, "Expected >= " + expectedMin + " results");
        ExtentManager.getTest().pass("Search '" + keyword + "' returned " + count + " product(s)");
    }

    @Test(groups = { "products", "positive" }, description = "Product detail page loads")
    public void testProductDetailPageLoads() {
        ensureWeAreOnProductsPage();
        products.clickViewFirstProduct();
        Assert.assertTrue(detail.isProductPageLoaded(), "Product detail page should load");
        ExtentManager.getTest().pass("Product detail loaded successfully");
    }

    @Test(dataProvider = "searchEdgeData", dataProviderClass = ProductCartDataProvider.class,
          groups = { "products", "edge" }, description = "Search handles edge-case inputs")
    public void testSearchEdgeCases(String keyword, String description) {
        ensureWeAreOnProductsPage();
        products.searchProduct(keyword);
        Assert.assertFalse(DriverManager.getDriver().getPageSource().contains("Uncaught"), "No JS crash for: " + description);
        ExtentManager.getTest().pass("Edge search handled safely: " + description);
    }
}