package tests;


import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import dragan.stojilkovic.Pages.LoginPage;
import dragan.stojilkovic.Pages.SearchBar;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Base class for all test classes.
 * Handles WebDriver setup, configuration loading, logging, and teardown.
 */
@Listeners({TestListener.class, RetryListener.class})
public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected LoginPage loginPage;
    protected SearchBar searchBar;
    protected Properties config;
    protected Logger log = LogManager.getLogger(getClass());

    /**
     * Initializes the browser, loads configuration, and navigates to the application URL.
     * @param browser Browser name (chrome, firefox, edge) – passed from TestNG XML or default chrome.
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        loadConfig();
        initializeDriver(browser);
        driver.manage().window().maximize();

        int defaultTimeout = Integer.parseInt(config.getProperty("default.wait", "10"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(defaultTimeout));

        String baseUrl = config.getProperty("app.url", "https://ananas.rs/login");
        driver.get(baseUrl);

        loginPage = new LoginPage(driver, wait);
        searchBar = new SearchBar(driver, wait);
        handlePopUps();

        log.info("Test setup completed for browser: {}", browser);
    }

    /** Loads configuration from src/test/resources/config.properties. */
    private void loadConfig() {
        config = new Properties();
        String configPath = "src/test/resources/config.properties";
        try (FileInputStream fis = new FileInputStream(configPath)) {
            config.load(fis);
            log.info("Configuration loaded from {}", configPath);
        } catch (IOException e) {
            log.error("Config file not found at {}, using defaults", configPath);
            config.setProperty("app.url", "https://ananas.rs/login");
            config.setProperty("default.wait", "10");
        }
    }

    /** Initializes the WebDriver with browser-specific options. */
    private void initializeDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOpts = new ChromeOptions();
                chromeOpts.addArguments("--disable-notifications");
                driver = new ChromeDriver(chromeOpts);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOpts = new FirefoxOptions();
                firefoxOpts.addPreference("dom.webnotifications.enabled", false);
                driver = new FirefoxDriver(firefoxOpts);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOpts = new EdgeOptions();
                edgeOpts.addArguments("--disable-notifications");
                driver = new EdgeDriver(edgeOpts);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        log.info("Initialized {} browser", browser);
    }

    /** Handles cookie consent and popups if present. */
    private void handlePopUps() {
        try {
            loginPage.acceptCookies();
            loginPage.closePopup();
        } catch (Exception e) {
            log.debug("No cookies or popups to handle: {}", e.getMessage());
        }
    }

    /** Closes the browser after each test method. */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("Browser closed");
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}