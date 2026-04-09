package tests;

import static org.testng.Assert.fail;import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Listeners(TestListener.class)
public class LoginTest extends BaseTest {

	private static final Logger log = LogManager.getLogger(LoginTest.class);

	// ========================= DATA PROVIDERS =========================

	@DataProvider(name = "validLoginData")
	public Object[][] validLoginData() {
		return new Object[][] { { config.getProperty("valid.email"), config.getProperty("valid.password"), "John" },
				{ config.getProperty("valid.email2"), config.getProperty("valid.password2"), "Ed" } };
	}

	@DataProvider(name = "invalidLoginCombos")
	public Object[][] invalidLoginCombos() {
		return new Object[][] { { config.getProperty("valid.email"), "wrongPass", "Email ili lozinka nisu u redu." },
				{ "invalid@example.com", config.getProperty("valid.password"), "Email ili lozinka nisu u redu." },
				{ "", config.getProperty("valid.password"), "Ovo polje je obavezno." },
				{ config.getProperty("valid.email"), "", "Ovo polje je obavezno." } };
	}

	@DataProvider(name = "passwordLengthBoundary")
	public Object[][] passwordLengthBoundary() {
		return new Object[][] { { "a", "Minimum 8 karaktera." }, { "abcdefg", "Minimum 8 karaktera." } };
	}

	@DataProvider(name = "invalidEmailFormats")
	public Object[][] invalidEmailFormats() {
		return new Object[][] { { "!@#$%^&*()", "Email adresa nije ispravna." },
				{ "wanaxa5524@craftapkcom", "Email adresa nije ispravna." },
				{ "wanaxa5524craftapk.com", "Email adresa nije ispravna." },
				{ "' OR 1=1 --", "Email adresa nije ispravna." },
				{ "<script>alert('XSS')</script>", "Email adresa nije ispravna." } };
	}

	@DataProvider(name = "responsiveDesignSizes")
	public Object[][] responsiveDesignSizes() {
		return new Object[][] { { 375, 812 }, // iPhone X
				{ 1024, 1366 } // iPad Pro
		};
	}

	// ========================= TESTS =========================

	@Test(dataProvider = "validLoginData")
	public void testValidLogin(String email, String password, String expectedUserName) {
	    loginPage.login(email, password);
	    // Wait for the logged-in user name element (more reliable than URL)
	    try {
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='sc-79pcpx-0 ieekjL']")));
	        Assert.assertEquals(loginPage.getLoggedInUserName(), expectedUserName, "Incorrect user name displayed.");
	    } catch (TimeoutException e) {
	        Assert.fail("Login failed – user name element not visible after login. Check credentials or application behavior.");
	    }
	    log.info("Valid login test passed for user: {}", email);
	}

	@Test(dataProvider = "invalidLoginCombos")
	public void testInvalidLoginCombinations(String email, String password, String expectedError) {
		SoftAssert soft = new SoftAssert();
		loginPage.login(email, password);
		if (email.isEmpty()) {
			soft.assertEquals(loginPage.getUsernameErrorMessage(), expectedError);
		} else if (password.isEmpty()) {
			soft.assertEquals(loginPage.getPasswordErrorMessage(), expectedError);
		} else {
			soft.assertEquals(loginPage.getErrorMessage(), expectedError);
		}
		soft.assertAll();
		log.info("Invalid login test executed for email: {}", email);
	}

	@Test
	public void testEmptyFields() {
		loginPage.clickLogin();
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(loginPage.getUsernameErrorMessage(), "Ovo polje je obavezno.");
		soft.assertEquals(loginPage.getPasswordErrorMessage(), "Ovo polje je obavezno.");
		soft.assertEquals(driver.getCurrentUrl(), "https://ananas.rs/login");
		soft.assertAll();
		log.info("Empty fields test passed");
	}

	@Test
	public void testEmptyEmailField() {
		String password = config.getProperty("valid.password");
		loginPage.login("", password);
		Assert.assertEquals(loginPage.getUsernameErrorMessage(), "Ovo polje je obavezno.");
		log.info("Empty email field test passed");
	}

	@Test
	public void testForgotPasswordLink() {
		loginPage.clickForgotPassword();
		wait.until(ExpectedConditions.urlToBe("https://ananas.rs/zaboravljena-lozinka"));
		Assert.assertEquals(driver.getCurrentUrl(), "https://ananas.rs/zaboravljena-lozinka");
		log.info("Forgot password link test passed");
	}

	@Test(enabled = false)
	public void testMaxPasswordLength() {
		String longPassword = "a".repeat(300);
		String email = config.getProperty("valid.email");
		loginPage.login(email, longPassword);
		// Known issue: app returns generic error instead of "Password too long."
		Assert.assertEquals(loginPage.getErrorMessage(), "Password too long.");
		log.warn(
				"Max password length test expects 'Password too long.', but app returns generic message – this is a known bug");
	}

	@Test(dataProvider = "passwordLengthBoundary")
	public void testPasswordLengthBoundary(String password, String expectedError) {
		String email = config.getProperty("valid.email");
		loginPage.login(email, password);
		Assert.assertEquals(loginPage.getMinPasswordLengthErrorMessage(), expectedError);
		log.info("Password length boundary test passed for password: {}", password);
	}

	@Test(enabled = false)
	public void testAccountLockoutAfterFailedAttempts() {
		String email = config.getProperty("valid.email");
		for (int i = 0; i < 5; i++) {
			loginPage.login(email, "invalidPass");
		}
		// Known issue: app does not lock account after 5 attempts
		Assert.assertEquals(loginPage.getErrorMessage(), "Nalog je zaključan zbog više neuspešnih pokušaja.");
		log.warn(
				"Account lockout test expects lock message, but app returns generic error – this is a known security bug");
	}

	@Test(dataProvider = "invalidEmailFormats")
	public void testInvalidEmailFormats(String email, String expectedError) {
		String password = config.getProperty("valid.password");
		loginPage.login(email, password);
		Assert.assertEquals(loginPage.getUsernameErrorMessage(), expectedError);
		log.info("Invalid email format test passed for email: {}", email);
	}

	@Test(dataProvider = "responsiveDesignSizes")
	public void testResponsiveDesign(int width, int height) {
	    driver.manage().window().setSize(new Dimension(width, height));
	    String email = config.getProperty("valid.email");
	    String password = config.getProperty("valid.password");
	    loginPage.login(email, password);
	    try {
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='sc-79pcpx-0 ieekjL']")));
	        // If we reach here, login succeeded
	    } catch (TimeoutException e) {
	        Assert.fail("Login failed on responsive design – user name element not visible.");
	    }
	    log.info("Responsive design test passed for {}x{}", width, height);
	}
}