package com.autoex.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

public class DriverManager {
    private static final ThreadLocal<WebDriver> TL = new ThreadLocal<>();

    public static void initDriver() {
        if (TL.get() != null) {
            return; 
        }

        boolean headless = ConfigReader.isHeadless();
        WebDriver driver;

        switch (ConfigReader.getBrowser().toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ff = new FirefoxOptions();
                if (headless) ff.addArguments("--headless");
                driver = new FirefoxDriver(ff);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions ed = new EdgeOptions();
                if (headless) ed.addArguments("--headless");
                driver = new EdgeDriver(ed);
                break;
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions ch = new ChromeOptions();
                if (headless) {
                    ch.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
                }
                ch.addArguments("--window-size=1920,1080", "--disable-extensions",
                                "--disable-blink-features=AutomationControlled");
                ch.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                driver = new ChromeDriver(ch);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        driver.manage().window().maximize();
        
        TL.set(driver);
    }

    public static WebDriver getDriver() {
        if (TL.get() == null) {
            throw new IllegalStateException("Driver not initialized. Please call initDriver() in @BeforeClass.");
        }
        return TL.get();
    }

    public static void quitDriver() {
        if (TL.get() != null) {
            TL.get().quit(); 
            TL.remove();   
        }
    }
}