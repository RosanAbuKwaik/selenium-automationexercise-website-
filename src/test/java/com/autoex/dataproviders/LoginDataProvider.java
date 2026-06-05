package com.autoex.dataproviders;

import org.testng.annotations.DataProvider;

public class LoginDataProvider {

    /**
     * Positive: credentials that exist on automationexercise.com.
     * Create these accounts once manually (or via registration test) before running.
     */
    @DataProvider(name = "loginPositiveData")
    public static Object[][] loginPositiveData() {
        return new Object[][] {
            // { email, password }
            { "test@test.com", "test123" },
        };
    }

    /**
     * Negative: wrong / missing credentials → login fails.
     */
    @DataProvider(name = "loginNegativeData")
    public static Object[][] loginNegativeData() {
        return new Object[][] {
            // { email, password, description }
            { "wrong@email.com",      "wrongpass",  "Non-existent account" },
            { "test@test.com",        "wrongpass",  "Valid email, wrong password" },
            { "",                     "test123",    "Empty email" },
            { "test@test.com",        "",           "Empty password" },
            { "",                     "",           "Both fields empty" },
            { "notanemail",           "test123",    "Invalid email format" },
        };
    }

    /**
     * Edge: injection attempts, whitespace, very long inputs.
     */
    @DataProvider(name = "loginEdgeData")
    public static Object[][] loginEdgeData() {
        return new Object[][] {
            // { email, password, description }
            { "' OR '1'='1'--",              "anything",   "SQL injection in email" },
            { "<script>alert(1)</script>",   "anything",   "XSS in email field" },
            { "a".repeat(255) + "@x.com",   "pass",       "Max-length email (255 chars)" },
            { " test@test.com ",             "test123",    "Email with surrounding whitespace" },
            { "TEST@TEST.COM",               "test123",    "Uppercase email" },
            { "test@test.com",               " ",          "Whitespace-only password" },
        };
    }
}
