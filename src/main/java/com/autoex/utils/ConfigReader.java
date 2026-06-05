package com.autoex.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config.properties: " + e.getMessage());
        }
    }

    public static String get(String key) {
        String v = props.getProperty(key);
        if (v == null) throw new RuntimeException("Missing property: " + key);
        return v.trim();
    }

    public static String getBaseUrl()       { return get("base.url"); }
    public static String getBrowser()       { return get("browser"); }
    public static int    getImplicitWait()  { return Integer.parseInt(get("implicit.wait")); }
    public static int    getExplicitWait()  { return Integer.parseInt(get("explicit.wait")); }
    public static boolean isHeadless()      { return Boolean.parseBoolean(get("headless")); }
    public static boolean screenshotOnFail(){ return Boolean.parseBoolean(get("screenshot.on.failure")); }
    public static String getReportsPath()   { return get("reports.path"); }
}
