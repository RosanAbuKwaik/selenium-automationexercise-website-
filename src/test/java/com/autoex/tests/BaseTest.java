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

    @BeforeMethod(alwaysRun = true)
    public void setUpMethod(Method method) {
        DriverManager.initDriver(); 
        this.driver = DriverManager.getDriver();
        
        driver.manage().window().maximize();
        ExtentManager.createTest(method.getName(), "Running test: " + method.getName());
    }
    
    @BeforeClass(alwaysRun = true) 
    public void setUpClass() {
        DriverManager.initDriver();
        this.driver = DriverManager.getDriver();
        driver.manage().window().maximize();
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDownMethod(ITestResult result) {
        ExtentTest test = ExtentManager.getTest();
        
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Failed: " + result.getThrowable().getMessage());
            
            if (ConfigReader.screenshotOnFail() && driver != null) {
                try {
                    String path = captureScreenshot(result.getName());
                    if (path != null) test.addScreenCaptureFromPath(path, "Failure Screenshot");
                } catch (Exception e) {
                    System.out.println("  Faild to catch: " + e.getMessage());
                }
            }
        }
        
        DriverManager.quitDriver();
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
        } catch (IOException e) { 
            return null; 
        }
    }
}