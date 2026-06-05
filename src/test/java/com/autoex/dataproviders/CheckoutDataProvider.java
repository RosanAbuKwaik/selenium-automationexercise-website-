package com.autoex.dataproviders;

import org.testng.annotations.DataProvider;

public class CheckoutDataProvider {

    @DataProvider(name = "checkoutPositiveData")
    public static Object[][] checkoutPositiveData() {
        return new Object[][] {
            { "Rosan Abukwaik",   "4111111111111111", "123", "12", "2026", "Please deliver after 5pm" },
            { "Rawan Doe",     "5500005555555559", "456", "06", "2027", "" },
            { "Alice Brown",  "371449635398431",  "789", "09", "2025", "Leave at reception" },
        };
    }

    @DataProvider(name = "checkoutNegativeData")
    public static Object[][] checkoutNegativeData() {
        return new Object[][] {
            { "",             "4111111111111111", "123", "12", "2026", "Empty card name" },
            { "Rosan Kwaik",   "",                 "123", "12", "2026", "Empty card number" },
            { "Rosan Kwaik",   "4111111111111111", "",    "12", "2026", "Empty CVC" },
            { "Rosan Kwaik",   "4111111111111111", "123", "",   "2026", "Empty expiry month" },
            { "Rosan Kwaik",   "4111111111111111", "123", "12", "",     "Empty expiry year" },
        };
    }

    @DataProvider(name = "checkoutEdgeData")
    public static Object[][] checkoutEdgeData() {
        return new Object[][] {
            // { cardName, cardNumber, cvc, expiryMonth, expiryYear, description }
            { "J",                    "4111111111111111", "123", "12", "2026", "Single-char card name" },
            { "N".repeat(100),        "4111111111111111", "123", "12", "2026", "100-char card name" },
            { "Rosan kwaik",           "0000000000000000", "000", "00", "0000", "All-zero card details" },
            { "Rosan kwaik",           "1234",             "1",   "1",  "25",   "Short card/cvc/date" },
            { "<script>alert(1)</>",  "4111111111111111", "123", "12", "2026", "XSS in card name" },
            { "' OR 1=1--",           "4111111111111111", "123", "12", "2026", "SQL injection in name" },
            { "Rosan kwaik",           "4111111111111111", "123", "12", "1999", "Expired year (1999)" },
            { "Rosan kwaik",           "4111111111111111", "123", "13", "2026", "Invalid month (13)" },
        };
    }
}
