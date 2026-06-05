package com.autoex.dataproviders;

import org.testng.annotations.DataProvider;

public class RegistrationDataProvider {

    /**
     * Positive: fresh unique email + valid details → account created successfully.
     * Timestamps in email ensure uniqueness across runs.
     */
    @DataProvider(name = "registrationPositiveData")
    public static Object[][] registrationPositiveData() {
        long ts = System.currentTimeMillis();
        return new Object[][] {
            // { name, email, password, day, month, year, firstName, lastName, address, state, city, zip, mobile }
            { "Alice",  "alice_"  + ts       + "@mailtest.dev", "Test@1234", "10", "5",  "1995", "Alice",  "Smith",   "123 Main St",       "California",  "Los Angeles", "90001", "5551112222" },
            { "Bob",    "bob_"    + (ts + 1) + "@mailtest.dev", "Secure#99", "22", "11", "1990", "Bob",    "Johnson", "456 Oak Avenue",    "New York",    "Brooklyn",    "11201", "5553334444" },
        };
    }

    /**
     * Negative: already-registered email, empty fields, invalid email format.
     */
    @DataProvider(name = "registrationNegativeData")
    public static Object[][] registrationNegativeData() {
        return new Object[][] {
            // { name, email, description }
            { "ExistingUser", "test@test.com",        "Already registered email" },
            { "",             "fresh@mailtest.dev",   "Empty name field" },
            { "ValidName",    "",                     "Empty email field" },
            { "ValidName",    "not-an-email",         "Invalid email format" },
            { "ValidName",    "missing@",             "Incomplete email (no domain)" },
        };
    }

    /**
     * Edge: boundary values — single-char name, very long name, special chars, numeric name.
     */
    @DataProvider(name = "registrationEdgeData")
    public static Object[][] registrationEdgeData() {
        long ts = System.currentTimeMillis();
        return new Object[][] {
            // { name, email, description }
            { "A",                                      "edge1_" + ts + "@mailtest.dev", "Single character name" },
            { "N".repeat(50),                           "edge2_" + ts + "@mailtest.dev", "50-character name" },
            { "John O'Brien",                           "edge3_" + ts + "@mailtest.dev", "Name with apostrophe" },
            { "用户名",                                 "edge4_" + ts + "@mailtest.dev", "Unicode / CJK name" },
            { "Name<script>alert(1)</script>",          "edge5_" + ts + "@mailtest.dev", "XSS in name field" },
            { "12345",                                  "edge6_" + ts + "@mailtest.dev", "Numeric-only name" },
            { "ValidName",                              " " + "edge7_" + ts + "@mailtest.dev", "Leading space in email" },
        };
    }
}
