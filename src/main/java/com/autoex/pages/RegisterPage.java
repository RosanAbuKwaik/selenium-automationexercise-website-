package com.autoex.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends BasePage {

    private By mrRadio = By.id("id_gender1");
    private By mrsRadio = By.id("id_gender2");
    private By passwordField = By.cssSelector("[data-qa='password']");
    private By daysDropdown = By.cssSelector("[data-qa='days']");
    private By monthsDropdown = By.cssSelector("[data-qa='months']");
    private By yearsDropdown = By.cssSelector("[data-qa='years']");
    private By newsletterCheckbox = By.id("newsletter");
    private By specialOffersCheckbox = By.id("optin");
    private By firstNameField = By.cssSelector("[data-qa='first_name']");
    private By lastNameField = By.cssSelector("[data-qa='last_name']");
    private By companyField = By.cssSelector("[data-qa='company']");
    private By addressField = By.cssSelector("[data-qa='address']");
    private By countryDropdown = By.cssSelector("[data-qa='country']");
    private By stateField = By.cssSelector("[data-qa='state']");
    private By cityField = By.cssSelector("[data-qa='city']");
    private By zipcodeField = By.cssSelector("[data-qa='zipcode']");
    private By mobileField = By.cssSelector("[data-qa='mobile_number']");
    private By createAccountButton = By.cssSelector("[data-qa='create-account']");
    private By accountCreatedHeader = By.cssSelector("[data-qa='account-created']");
    private By continueButton = By.cssSelector("[data-qa='continue-button']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void waitForPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
    }

    public void selectGender(String gender) {
        if ("Mrs".equalsIgnoreCase(gender)) jsClick(driver.findElement(mrsRadio));
        else jsClick(driver.findElement(mrRadio));
    }

    public void fillAccountInfo(String password, String day, String month, String year) {
        type(passwordField, password);
        selectByValue(daysDropdown, day);
        selectByValue(monthsDropdown, month);
        selectByValue(yearsDropdown, year);
    }

    public void fillAddressInfo(String firstName, String lastName, String address,
                                String state, String city, String zip, String mobile) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(addressField, address);
        selectByVisibleText(countryDropdown, "United States");
        type(stateField, state);
        type(cityField, city);
        type(zipcodeField, zip);
        type(mobileField, mobile);
    }

    public void clickCreateAccount() {
        scrollToElement(createAccountButton);
        click(createAccountButton);
        sleep(2000);
    }

    public boolean isAccountCreated() {
        try {
            return isVisible(accountCreatedHeader);
        } catch (Exception e) { return false; }
    }

    public String getAccountCreatedText() {
        try { return getText(accountCreatedHeader); } catch (Exception e) { return ""; }
    }

    public void clickContinue() {
        click(continueButton);
        sleep(1000);
    }
}