package com.autoex.pages;

import com.autoex.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    
    private By signupLogin = By.cssSelector("a[href='/login']");
    private By logout = By.cssSelector("a[href='/logout']");
    private By products = By.cssSelector("a[href='/products']");
    private By cart = By.cssSelector("a[href='/view_cart']");
    private By contactUs = By.cssSelector("a[href='/contact_us']");
    private By home = By.cssSelector("li a[href='/']");
    private By deleteAccount= By.cssSelector("a[href='/delete_account']");
    private By loggedInUsername = By.tagName("b");
    private By heroCarousel = By.id("slider-carousel");

    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    public void navigateTo() {
        driver.get(ConfigReader.getBaseUrl());
        wait.until(ExpectedConditions.visibilityOfElementLocated(signupLogin));
    }

    public void clickSignupLogin()  { click(signupLogin); }
    public void clickProducts()     { click(products); }
    public void clickCart()         { click(cart); }
    public void clickContactUs()    { click(contactUs); }
    public void clickHome()         { click(home); }

    public void clickLogout() {
        scrollToElement(logout);
        click(logout);
        sleep(1000);
    }

    public boolean isLogoutVisible() {
        return driver.findElements(logout).size() > 0;
    }

    public boolean isSignupLoginVisible() {
        return isVisible(signupLogin);
    }

    public String getLoggedInUsername() {
        try { return getText(loggedInUsername); }
        catch (Exception e) { return ""; }
    }

    public boolean isHomePageLoaded() {
        return isVisible(heroCarousel);
    }

    public void deleteAccount() {
        scrollToElement(deleteAccount);
        click(deleteAccount);
        sleep(1500);
    }
}