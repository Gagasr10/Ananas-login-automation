package tests;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class) // Listener for screenshots
public class LoginTest extends BaseTest {

    @Test // 1 - Valid login with correct credentials
    public void testValidLogin() {
        String email = config.getProperty("valid.email");
        String password = config.getProperty("valid.password");
        loginPage.login(email, password);
        // TODO: After successful login, URL should not remain "/login" -可能需要修改
        Assert.assertEquals(driver.getCurrentUrl(), "https://ananas.rs/login");
        String expectedUserName = "John";
        Assert.assertEquals(loginPage.getLoggedInUserName(), expectedUserName, "Incorrect user name displayed.");
    }

    @Test // 2 - Valid login with a two-character username
    public void testValidLoginTwoCharactersName() {
        String email = config.getProperty("valid.email2");
        String password = config.getProperty("valid.password2");
        loginPage.login(email, password);
        Assert.assertEquals(driver.getCurrentUrl(), "https://ananas.rs/login");
        String expectedUserName = "Ed";
        Assert.assertEquals(loginPage.getLoggedInUserName(), expectedUserName, "Incorrect user name displayed.");
    }

    @Test // 3 - Invalid password with valid email
    public void testInvalidPassword() {
        String email = config.getProperty("valid.email");
        loginPage.login(email, "invalidPass");
        Assert.assertEquals(loginPage.getErrorMessage(), "Email ili lozinka nisu u redu.");
    }

    @Test // 4 - Mixed valid and invalid credentials (valid email, wrong password from another user)
    public void testInvalidLogin_MixedCredentials() {
        String email = config.getProperty("valid.email");
        String wrongPassword = config.getProperty("valid.password2"); // different user's password
        loginPage.login(email, wrongPassword);
        Assert.assertEquals(loginPage.getErrorMessage(), "Email ili lozinka nisu u redu.");
    }

    @Test // 5 - Invalid email with valid password
    public void testInvalidEmailValidPassword() {
        String password = config.getProperty("valid.password");
        loginPage.login("invalidUser@fake.com", password);
        Assert.assertEquals(loginPage.getErrorMessage(), "Email ili lozinka nisu u redu.");
    }

    @Test // 6 - Empty email and password fields
    public void testEmptyFields() {
        loginPage.clickLogin();
        Assert.assertEquals(loginPage.getUsernameErrorMessage(), "Ovo polje je obavezno.");
        Assert.assertEquals(loginPage.getPasswordErrorMessage(), "Ovo polje je obavezno.");
        Assert.assertEquals(driver.getCurrentUrl(), "https://ananas.rs/login");
    }

    @Test // 7 - Empty email field, valid password
    public void testEmptyEmailField() {
        String password = config.getProperty("valid.password");
        loginPage.login("", password);
        Assert.assertEquals(loginPage.getUsernameErrorMessage(), "Ovo polje je obavezno.");
    }

    @Test // 8 - Forgot password link functionality
    public void testForgotPasswordLink() {
        loginPage.clickForgotPassword();
        wait.until(ExpectedConditions.urlToBe("https://ananas.rs/zaboravljena-lozinka"));
        Assert.assertEquals(driver.getCurrentUrl(), "https://ananas.rs/zaboravljena-lozinka");
    }

    @Test // 9 - Maximum password length (300 characters)
    public void testMaxPasswordLength() {
        String longPassword = "a".repeat(300);
        String email = config.getProperty("valid.email");
        loginPage.login(email, longPassword);
        Assert.assertEquals(loginPage.getErrorMessage(), "Password too long.");
    }

    @Test // 10 - Minimum password length (less than 8 characters)
    public void testMinPasswordLength() {
        String email = config.getProperty("valid.email");
        loginPage.login(email, "a");
        Assert.assertEquals(loginPage.getMinPasswordLengthErrorMessage(), "Minimum 8 karaktera.");
    }

    @Test // 11 - Password boundary: exactly 7 characters
    public void testPasswordBoundary_SevenCharacters() {
        String email = config.getProperty("valid.email");
        loginPage.login(email, "abcdefg");
        Assert.assertEquals(loginPage.getMinPasswordLengthErrorMessage(), "Minimum 8 karaktera.");
    }

    @Test // 12 - Account lockout after multiple failed attempts (security)
    public void testAccountLockoutAfterFailedAttempts() {
        String email = config.getProperty("valid.email");
        for (int i = 0; i < 5; i++) {
            loginPage.login(email, "invalidPass");
        }
        Assert.assertEquals(loginPage.getErrorMessage(), "Nalog je zaključan zbog više neuspešnih pokušaja.");
    }

    @Test // 13 - Special characters in email address
    public void testSpecialCharactersInEmailAddress() {
        String password = config.getProperty("valid.password");
        loginPage.login("!@#$%^&*()", password);
        Assert.assertEquals(loginPage.getUsernameErrorMessage(), "Email adresa nije ispravna.");
    }

    @Test // 14 - Invalid email format (missing dot after domain)
    public void testInvalidEmailFormat() {
        loginPage.login("wanaxa5524@craftapkcom", "Dzoni100");
        Assert.assertEquals(loginPage.getUsernameErrorMessage(), "Email adresa nije ispravna.");
    }

    @Test // 15 - Missing '@' symbol in email
    public void testInvalidEmail_MissingAtSymbol() {
        loginPage.login("wanaxa5524craftapk.com", "Dzoni100");
        Assert.assertEquals(loginPage.getUsernameErrorMessage(), "Email adresa nije ispravna.");
    }

    @Test // 16 - SQL injection attempt in username field
    public void testSQLInjection() {
        loginPage.login("' OR 1=1 --", "password");
        Assert.assertEquals(loginPage.getUsernameErrorMessage(), "Email adresa nije ispravna.");
    }

    @Test // 17 - XSS attempt in username field
    public void testXSSInUsername() {
        String password = config.getProperty("valid.password");
        loginPage.login("<script>alert('XSS')</script>", password);
        Assert.assertEquals(loginPage.getUsernameErrorMessage(), "Email adresa nije ispravna.");
    }

    @Test // 18 - Responsive design: mobile (iPhone X dimensions)
    public void testMobileResponsiveDesign() {
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 812));
        String email = config.getProperty("valid.email");
        String password = config.getProperty("valid.password");
        loginPage.login(email, password);
        Assert.assertEquals(driver.getCurrentUrl(), "https://ananas.rs/login");
    }

    @Test // 19 - Responsive design: tablet (iPad Pro dimensions)
    public void testTabletResponsiveDesign() {
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1024, 1366));
        String email = config.getProperty("valid.email");
        String password = config.getProperty("valid.password");
        loginPage.login(email, password);
        Assert.assertEquals(driver.getCurrentUrl(), "https://ananas.rs/login");
    }
}