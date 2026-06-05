package com.autoex.utils;

import com.github.javafaker.Faker;

import java.util.Locale;

public class FakerUtil {
    private static final Faker faker = new Faker(Locale.ENGLISH);

    public static String randomEmail() {
        return "autotest_" + System.currentTimeMillis() + "@mailtest.dev";
    }

    public static String randomPassword() {
        return "Test@" + faker.number().numberBetween(1000, 9999);
    }

    public static String randomName() {
        return faker.name().firstName();
    }

    public static String randomLastName() {
        return faker.name().lastName();
    }

    public static String randomAddress() {
        return faker.address().streetAddress();
    }

    public static String randomCity() {
        return faker.address().city();
    }

    public static String randomState() {
        return faker.address().state();
    }

    public static String randomZip() {
        return faker.address().zipCode().split("-")[0];
    }

    public static String randomPhone() {
        return faker.numerify("##########");
    }
}
