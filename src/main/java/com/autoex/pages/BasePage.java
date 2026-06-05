package com.autoex.pages;

import com.autoex.utils.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.autoex.utils.ConfigReader.getExplicitWait;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(getExplicitWait()));
        PageFactory.initElements(driver, this);
    }
    
   
    
    public void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                element.clear();
        element.sendKeys(text);
    }
    
    
    public void click(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        click(element); 
    }
    
    
    public void jsClick(WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
    
        public void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public boolean isVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }
    
    
    
    
    protected void type(WebElement el, String text) {
        wait.until(ExpectedConditions.visibilityOf(el)).clear();
        el.sendKeys(text);
    }

    protected String getText(WebElement el) {
        return wait.until(ExpectedConditions.visibilityOf(el)).getText().trim();
    }

    protected boolean isVisible(WebElement el) {
        try { return wait.until(ExpectedConditions.visibilityOf(el)).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public void selectByVisibleText(By locator, String text) {
        WebElement element = driver.findElement(locator);
        org.openqa.selenium.support.ui.Select dropdown = new org.openqa.selenium.support.ui.Select(element);
        dropdown.selectByVisibleText(text);
    }

    public void selectByValue(By locator, String value) {
        WebElement element = driver.findElement(locator);
        org.openqa.selenium.support.ui.Select dropdown = new org.openqa.selenium.support.ui.Select(element);
        dropdown.selectByValue(value);
    }
    
    
    protected void selectByVisibleText(WebElement el, String text) {
        new Select(wait.until(ExpectedConditions.visibilityOf(el))).selectByVisibleText(text);
    }

    protected void scrollToElement(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }
   

    protected void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    protected boolean elementExists(By locator) {
        try { return !driver.findElements(locator).isEmpty(); }
        catch (Exception e) { return false; }
    }

    public String getTitle()      { return driver.getTitle(); }
    public String getCurrentUrl() { return driver.getCurrentUrl(); }
}
