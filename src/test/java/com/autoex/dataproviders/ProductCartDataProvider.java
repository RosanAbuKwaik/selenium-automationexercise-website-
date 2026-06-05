package com.autoex.dataproviders;

import org.testng.annotations.DataProvider;

public class ProductCartDataProvider {

    /**
     * Positive search: keywords that match real products on the site.
     */
    @DataProvider(name = "searchPositiveData")
    public static Object[][] searchPositiveData() {
        return new Object[][] {
            // { keyword, expectedMinResults }
            { "top",    1 },
            { "dress",  1 },
            { "jeans",  1 },
            { "t-shirt",1 },
            { "saree",  1 },
        };
    }

    /**
     * Negative search: garbage / no-match keywords → 0 results (or empty state).
     */
    @DataProvider(name = "searchNegativeData")
    public static Object[][] searchNegativeData() {
        return new Object[][] {
            // { keyword, description }
            { "zxqjwxyz999",    "Gibberish keyword" },
            { "💻🖥️",           "Emoji keyword" },
            { "   ",            "Whitespace-only keyword" },
        };
    }

    /**
     * Edge search: special chars, very long strings, SQL / XSS payloads.
     */
    @DataProvider(name = "searchEdgeData")
    public static Object[][] searchEdgeData() {
        return new Object[][] {
            // { keyword, description }
            { "' OR 1=1--",                    "SQL injection in search" },
            { "<script>alert(1)</script>",     "XSS payload in search" },
            { "a".repeat(200),                 "200-char search string" },
            { "DRESS",                         "All-uppercase keyword" },
            { "dr ess",                        "Keyword with internal space" },
            { "dress#@!",                      "Special chars appended" },
        };
    }

    /**
     * Positive cart: product indices to add and verify.
     */
    @DataProvider(name = "cartPositiveData")
    public static Object[][] cartPositiveData() {
        return new Object[][] {
            // { productIndex, description }
            { 0, "Add first product" },
            { 1, "Add second product" },
            { 2, "Add third product" },
        };
    }
}
