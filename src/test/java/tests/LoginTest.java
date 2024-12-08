package tests;


import java.time.Duration;
import java.awt.Dimension;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import dragan.stojilkovic.Pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;


@Listeners(TestListener.class) // Listener for screenshots
public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;

    // Getter for WebDriver so listener can access it
    public WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        driver.manage().window().maximize();
        driver.get("https://ananas.rs/login");

        loginPage = new LoginPage(driver);
    }

    @Test  //1.
    public void testValidLogin() throws InterruptedException {
        // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        
        // Close popup if it appears
        loginPage.closePopup();

        // Perform login using valid credentials
        loginPage.login("wanaxa5524@craftapk.com", "John1000");
        
        // Assert that the user is redirected to the correct URL after a successful login
        Assert.assertEquals(driver.getCurrentUrl(), "https://ananas.rs/login");

        // Assert that the correct user name is displayed after login
        String expectedUserName = "John";
        Assert.assertEquals(loginPage.getLoggedInUserName(), expectedUserName, "Logged-in user's name should be displayed correctly.");
    }

    @Test  //2.
    public void testValidLoginTwoCharactersName() {
        // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        
        // Close popup if it appears
        loginPage.closePopup();

        // Perform login using valid credentials
        loginPage.login("ub4r3t474c@flashpost.net", "EdCA1000");

        // Assert that the user is redirected to the correct URL after a successful login
        Assert.assertEquals(driver.getCurrentUrl(), "https://ananas.rs/login");

        // Assert that the correct user name is displayed after login
        String expectedUserName = "Ed";
        Assert.assertEquals(loginPage.getLoggedInUserName(), expectedUserName, "Logged-in user's name should be displayed correctly.");
    }



    @Test  //3.
    public void testInvalidPassword() {
        // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        
     // Close popup if it appears
        loginPage.closePopup();

        // Use the login method to perform login with valid username and invalid password
        loginPage.login("wanaxa5524@craftapk.com", "invalidPass");

        // Assert that the correct error message is displayed
        Assert.assertEquals(loginPage.getErrorMessage(), "Email ili lozinka nisu u redu.");
    }


    @Test  //4.
    public void testInvalidLogin_MixedCredentials() {
        // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        loginPage.closePopup();

        // Use the login method with mixed credentials
        loginPage.login("wanaxa5524@craftapk.com", "EdLa1000");

        // Assert that the correct error message is displayed
        Assert.assertEquals(loginPage.getErrorMessage(), "Email ili lozinka nisu u redu.");
    }


    @Test  //5.
    public void testInvalidEmailValidPassworld() {
        // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        
     // Close popup if it appears
        loginPage.closePopup();

        // Use the login method with an invalid username and valid password
        loginPage.login("invalidUser@fake.com", "John1000");

        // Assert that the correct error message is displayed
        Assert.assertEquals(loginPage.getErrorMessage(), "Email ili lozinka nisu u redu.");
    }


    @Test  //6
    public void testEmptyFields() {
        // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        
     // Close popup if it appears
        loginPage.closePopup();

        // Click the login button without entering any data
        loginPage.clickLogin();
        
        // Assert that error messages are displayed for empty fields
        Assert.assertEquals(loginPage.getUsernameErrorMessage(), "Ovo polje je obavezno.");
        Assert.assertEquals(loginPage.getPasswordErrorMessage(), "Ovo polje je obavezno.");
        Assert.assertEquals(driver.getCurrentUrl(), "https://ananas.rs/login");

    }

    @Test  //7
    public void testEmptyEmailField() {
        // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        
     // Close popup if it appears
        loginPage.closePopup();

        // Perform login with an empty email field and valid password
        loginPage.login("", "John1000");
        
        // Assert that the username field shows the correct error message
        Assert.assertEquals(loginPage.getUsernameErrorMessage(), "Ovo polje je obavezno.");
    }

    @Test  //8
    public void testForgotPasswordLink() throws InterruptedException {
    	  // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        
     // Close popup if it appears
        loginPage.closePopup();

        // Click on the 'Forgot Password' link
        loginPage.clickForgotPassword();
        
        // Use WebDriverWait instead of Thread.sleep
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.urlToBe("https://ananas.rs/zaboravljena-lozinka"));
        
        // Assert that the user is redirected to the forgot password page
        Assert.assertEquals(driver.getCurrentUrl(), "https://ananas.rs/zaboravljena-lozinka");
    }

    @Test  //9
    public void testMaxPasswordLength() {
        // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        
     // Close popup if it appears
        loginPage.closePopup();

        // Perform login with a valid email and an overly long password
        String longPassword = "a".repeat(300);
        loginPage.login("wanaxa5524@craftapk.com", longPassword);
        
        // Assert that the correct error message is displayed for a password that's too long
        Assert.assertEquals(loginPage.getErrorMessage(), "Password too long.");
    }

    @Test  //10
    public void testMinPasswordLength() throws InterruptedException {
    	  // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        
     // Close popup if it appears
       loginPage.closePopup();

        // Perform login with a valid email and a password that's too short
        loginPage.login("wanaxa5524@craftapk.com", "a");
        
        // Wait for the error message to be visible (using the WebElement from LoginPage)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(loginPage.minPasswordLengthErrorMessage));
        
        // Assert that the correct error message is displayed for a password that's too short
        Assert.assertEquals(loginPage.getMinPasswordLengthErrorMessage(), "Minimum 8 karaktera.");
    }

    @Test  //11
    public void testPasswordBoundary_SevenCharacters() {
        // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        
     // Close popup if it appears
        loginPage.closePopup();

        // Perform login with a valid email and a 7-character password
        loginPage.login("wanaxa5524@craftapk.com", "abcdefg");
        
        // Assert that the correct error message is displayed for a password that's too short
        Assert.assertEquals(loginPage.getMinPasswordLengthErrorMessage(), "Minimum 8 karaktera.");
    }

    @Test  //12
    public void testAccountLockoutAfterFailedAttempts() throws InterruptedException {
        // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        
     // Close popup if it appears
        loginPage.closePopup();

        // Attempt to login multiple times with invalid credentials
        for (int i = 0; i <= 5; i++) {
            loginPage.login("wanaxa5524@craftapk.com", "invalidPass");
        }
        
        // Assert that the account is locked after too many failed login attempts
        Assert.assertEquals(loginPage.getErrorMessage(), "Nalog je zaključan zbog više neuspešnih pokušaja.");
    }

    @Test  //13
    public void testSpecialCharactersInEmailAddress()  {
        // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        
     // Close popup if it appears
        loginPage.closePopup();

        // Perform login with special characters in the username and a valid password
        loginPage.login("!@#$%^&*()", "John1000");
       
        
        // Assert that the correct error message is displayed
        Assert.assertEquals(loginPage.getUsernameErrorMessage(), "Email adresa nije ispravna.");
    }

    @Test  //14
    public void testInvalidEmailFormat() {
        // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        
     // Close popup if it appears
        loginPage.closePopup();

        // Perform login with an invalid email format and a valid password
        loginPage.login("wanaxa5524@craftapkcom", "Dzoni100");
        
        // Assert that the correct error message is displayed for an invalid email format
        Assert.assertEquals(loginPage.getUsernameErrorMessage(), "Email adresa nije ispravna.");
    }

    @Test  //15
    public void testInvalidEmail_MissingAtSymbol() {
        // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        
     // Close popup if it appears
        loginPage.closePopup();

        // Perform login with a missing '@' symbol in the email and a valid password
        loginPage.login("wanaxa5524craftapk.com", "Dzoni100");
        
        // Assert that the correct error message is displayed for the invalid email format
        Assert.assertEquals(loginPage.getUsernameErrorMessage(), "Email adresa nije ispravna.");
    }

    @Test  //16
    public void testSQLInjection()  {
        // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        
        // Close popup if it appears
        loginPage.closePopup();

        // Perform login with an SQL injection attempt in the username
        loginPage.login("' OR 1=1 --", "password");
                      
        // Assert that the correct error message is displayed
        Assert.assertEquals(loginPage.getUsernameErrorMessage(), "Email adresa nije ispravna.");
    }

    @Test  //17
    public void testXSSInUsername() {
        // Accept cookies before performing any other actions
        loginPage.acceptCookies();
        
     // Close popup if it appears
        loginPage.closePopup();

        // Perform login with an XSS attempt in the username
        loginPage.login("<script>alert('XSS')</script>", "John1000");
        
        // Assert that the correct error message is displayed
        Assert.assertEquals(loginPage.getUsernameErrorMessage(), "Email adresa nije ispravna.");
    }
    
   
    @Test  //18
    public void testMobileResponsiveDesign() {
    	// Set the resolution for a mobile device (eg iPhone X)
        loginPage.acceptCookies();
        
     // Close popup if it appears
        loginPage.closePopup();
        
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 812)); // iPhone X dimensions
        
        loginPage.login("wanaxa5524@craftapk.com", "John1000");

        // Assert that the login form is displayed correctly on mobile
        Assert.assertEquals(driver.getCurrentUrl(), "https://ananas.rs/login");
        
       
    }

    @Test  //19
    public void testTabletResponsiveDesign() {
    	// Set resolution for tablet device (eg iPad Pro)
        loginPage.acceptCookies();
        
     // Close popup if it appears
        loginPage.closePopup();
        
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1024, 1366)); // iPad Pro dimensions
        
        loginPage.login("wanaxa5524@craftapk.com", "John1000");

        // Assert that the login form is displayed correctly on tablet
        Assert.assertEquals(driver.getCurrentUrl(), "https://ananas.rs/login");
        
              
    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();  // Close browser after each test
        }
    }
}
