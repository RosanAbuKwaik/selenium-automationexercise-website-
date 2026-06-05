package com.autoex.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private By loginEmail = By.cssSelector("[data-qa='login-email']");
    private By loginPassword = By.cssSelector("[data-qa='login-password']");
    private By loginButton = By.cssSelector("[data-qa='login-button']");
    private By loginErrorMsg = By.cssSelector(".login-form p[style]");
    private By signupName = By.cssSelector("[data-qa='signup-name']");
    private By signupEmail = By.cssSelector("[data-qa='signup-email']");
    private By signupButton = By.cssSelector("[data-qa='signup-button']");
    private By signupErrorMsg = By.cssSelector(".signup-form p[style]");
    private By loginHeading = By.cssSelector(".login-form h2");
    private By signupHeading = By.cssSelector(".signup-form h2");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void waitForPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginEmail));
    }

    public void enterLoginEmail(String email)         { type(loginEmail, email); }
    public void enterLoginPassword(String password)  { type(loginPassword, password); }
    public void clickLoginButton()                  { click(loginButton); sleep(1500); }

    public void login(String email, String password) {
        waitForPage();
        enterLoginEmail(email);
        enterLoginPassword(password);
        clickLoginButton();
    }

    public boolean isLoginErrorVisible() {
        return isVisible(loginErrorMsg);
    }

    public String getLoginErrorText() {
        try { return getText(loginErrorMsg); } catch (Exception e) { return ""; }
    }

    public void enterSignupName(String name)    { type(signupName, name); }
    public void enterSignupEmail(String email) { type(signupEmail, email); }
    public void clickSignupButton()            { click(signupButton); sleep(1500); }

    public void fillSignupForm(String name, String email) {
        waitForPage();
        enterSignupName(name);
        enterSignupEmail(email);
        clickSignupButton();
    }

    public boolean isSignupErrorVisible() {
        return isVisible(signupErrorMsg);
    }

    public String getSignupErrorText() {
        try { return getText(signupErrorMsg); } catch (Exception e) { return ""; }
    }

    public String getLoginHeading()  { return getText(loginHeading); }
    public String getSignupHeading() { return getText(signupHeading); }
}