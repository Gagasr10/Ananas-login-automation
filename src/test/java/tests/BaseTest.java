package tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import dragan.stojilkovic.Pages.LoginPage;
import dragan.stojilkovic.Pages.SearchBar;
import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(TestListener.class)
public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected LoginPage loginPage;
    protected SearchBar searchBar;
    protected Properties config;  // Added: for loading config.properties

    // Public getter for WebDriver
    public WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        // Load config file (email, password, URL, timeout...)
        loadConfig();

        // Initialize the WebDriver based on the selected browser
        initializeDriver(browser);

        // Set browser properties
        driver.manage().window().maximize();

        // Define wait object (timeout can also be taken from config if desired)
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navigate to the application URL - now from config file
        String baseUrl = config.getProperty("app.url", "https://ananas.rs/login");
        driver.get(baseUrl);

        // Initialize page objects
        loginPage = new LoginPage(driver);
        searchBar = new SearchBar(driver);

        // Handle cookies and popups
        handlePopUps();
    }

    /**
     * Loads the config.properties file from src/test/resources
     */
    private void loadConfig() {
        config = new Properties();
        String configPath = "src/test/resources/config.properties";
        try (FileInputStream fis = new FileInputStream(configPath)) {
            config.load(fis);
            System.out.println("Config file loaded successfully.");
        } catch (IOException e) {
            System.out.println("Warning: Config file not found at " + configPath + ". Using default values.");
            // If file does not exist, set default values (optional)
            config.setProperty("app.url", "https://ananas.rs/login");
        }
    }

    private void initializeDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                System.out.println("Initialized Chrome browser.");
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                System.out.println("Initialized Firefox browser.");
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                System.out.println("Initialized Edge browser.");
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private void handlePopUps() {
        try {
            loginPage.acceptCookies();
            loginPage.closePopup();
            System.out.println("Handled cookies and popups.");
        } catch (Exception e) {
            System.out.println("No cookies or popups to handle.");
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed.");
        }
    }
}