package com.autoex.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(ConfigReader.getReportsPath());
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("AutomationExercise Test Report");
            spark.config().setReportName("Selenium Java Framework — automationexercise.com");
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Site Under Test", ConfigReader.getBaseUrl());
            extent.setSystemInfo("Browser", ConfigReader.getBrowser());
        }
        return extent;
    }

    public static ExtentTest createTest(String name, String desc) {
        ExtentTest t = getInstance().createTest(name, desc);
        TEST.set(t);
        return t;
    }

    public static ExtentTest getTest() { return TEST.get(); }

    public static void flush() { if (extent != null) extent.flush(); }
}
