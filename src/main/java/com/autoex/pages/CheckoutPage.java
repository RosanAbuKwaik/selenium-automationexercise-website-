package com.autoex.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    private By deliveryAddress = By.id("address_delivery");
    private By orderRows = By.cssSelector("#cart_info tbody tr");
    private By commentField = By.cssSelector(".form-control[name='message']");
    private By placeOrderButton = By.cssSelector("a[href='/payment']");

    private By cardName = By.cssSelector("[data-qa='name-on-card']");
    private By cardNumber = By.cssSelector("[data-qa='card-number']");
    private By cvcField = By.cssSelector("[data-qa='cvc']");
    private By expiryMonth = By.cssSelector("[data-qa='expiry-month']");
    private By expiryYear = By.cssSelector("[data-qa='expiry-year']");
    private By payButton = By.cssSelector("[data-qa='pay-button']");

    private By orderPlacedHeader = By.cssSelector("[data-qa='order-placed']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCheckoutPageLoaded() {
        return isVisible(deliveryAddress);
    }

    public String getDeliveryAddressText() {
        try { return getText(deliveryAddress); } catch (Exception e) { return ""; }
    }

    public int getOrderItemCount() {
        return driver.findElements(orderRows).size();
    }

    public void addComment(String comment) {
        type(commentField, comment);
    }

    public void clickPlaceOrder() {
        scrollToElement(placeOrderButton);
        click(placeOrderButton);
        sleep(1500);
    }

    public void fillPaymentDetails(String name, String number, String cvc, String month, String year) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardName));
        type(cardName, name);
        type(cardNumber, number);
        type(cvcField, cvc);
        type(expiryMonth, month);
        type(expiryYear, year);
    }

    public void clickPayAndConfirm() {
        click(payButton);
        sleep(3000);
    }

    public boolean isOrderPlaced() {
        try {
            return isVisible(orderPlacedHeader);
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("payment_done") || 
                   driver.findElements(orderPlacedHeader).size() > 0;
        }
    }

    public String getOrderConfirmationText() {
        try { return getText(orderPlacedHeader); } catch (Exception e) { return ""; }
    }
}