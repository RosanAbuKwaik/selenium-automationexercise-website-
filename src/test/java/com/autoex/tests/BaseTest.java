package com.autoex.tests;

import com.autoex.utils.DriverManager;
import com.autoex.utils.ExtentManager;
import com.autoex.utils.ConfigReader;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    protected WebDriver driver; 

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        DriverManager.initDriver();
        this.driver = DriverManager.getDriver();
    }
    
    
    

    @BeforeMethod(alwaysRun = true)
    public void setUpMethod(Method method) {
        Test annotation = method.getAnnotation(Test.class);
        String desc = (annotation != null) ? annotation.description() : "";
        ExtentManager.createTest(method.getName(), desc);
    }


    
    @AfterMethod(alwaysRun = true)
    public void tearDownMethod(ITestResult result) {
        ExtentTest test = ExtentManager.getTest();
        
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, result.getThrowable().getMessage());
                if (ConfigReader.screenshotOnFail() && driver != null) { 
                String path = captureScreenshot(result.getName());
                if (path != null) {
                    try { test.addScreenCaptureFromPath(path, "Failure"); }
                    catch (Exception ignored) {}
                }
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "PASSED");
        } else {
            test.log(Status.SKIP, "SKIPPED");
        }
    }
        @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        DriverManager.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() { 
        ExtentManager.flush(); 
    }

    private String captureScreenshot(String testName) {
        try {
            String dir = "test-output/screenshots";
            Files.createDirectories(Paths.get(dir));
            String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String out = dir + "/" + testName + "_" + ts + ".png";
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(src.toPath(), Paths.get(out));
            return out;
        } catch (IOException e) { return null; }
    }
}