package dragan.stojilkovic.Pages;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    // Username input field located by XPath
    @FindBy(xpath = "//input[@id='username']")
    WebElement username;

    // Password input field located by XPath
    @FindBy(xpath = "//input[@id='password']")
    WebElement password;

    // Login button located by XPath
    @FindBy(xpath = "//button[@id='login-submit']")
    WebElement loginBtn;

    // Error message displayed when login fails, located by XPath
    @FindBy(xpath = "//p[contains(text(),'Email ili lozinka nisu u redu')]")
    WebElement loginErrorMessage;

    // Accept cookies button located by XPath
    @FindBy(xpath = "//button[text()='Slažem se']")
    WebElement acceptCookiesButton;

    // Remember me checkbox located by XPath
    @FindBy(xpath = "//input[@id='rememberMeCheckbox']")
    WebElement rememberMeCheckbox;

    // Forgot password link located by XPath
    @FindBy(xpath = "//a[span[text()='Zaboravili ste lozinku?']]")
    WebElement forgotPasswordLink;

    @FindBy(xpath = "//div[@id='errorMessage']")
    WebElement errorMessag;

    @FindBy(xpath = "(//p[contains(@class, 'Mui-error')])[1]")
    private WebElement usernameErrorMessage;

    @FindBy(xpath = "(//p[contains(@class, 'Mui-error')])[2]")
    private WebElement passwordErrorMessage;

    @FindBy(xpath = "//p[contains(text(), 'Minimum 8 karaktera.')]")
    public WebElement minPasswordLengthErrorMessage;

    @FindBy(xpath = "//span[@class='sc-79pcpx-0 ieekjL']")
    WebElement userName;

    // Close popup button
    @FindBy(xpath = "//button[@aria-label='zatvori popup']")
    WebElement closeButton;
    
    

    // Constructor for the LoginPage class that initializes web elements using PageFactory
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));  // WebDriverWait set to 5 seconds
        PageFactory.initElements(driver, this);
    }

    // Method to set the username in the username input field
    public void setUsername(String user) {
        username.sendKeys(user);
    }

    // Method to set the password in the password input field
    public void setPassword(String pass) {
        password.sendKeys(pass);
    }

    // Method to click the login button
    public void clickLogin() {
        loginBtn.click();
    }

    // Combined method for login
    public void login(String user, String pass) {
        setUsername(user);
        setPassword(pass);
        clickLogin();
    }

    // Method to retrieve the error message displayed after a failed login attempt
    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(loginErrorMessage));  // Wait until the login error message is visible
        return loginErrorMessage.getText();
    }

    // Method to click the accept cookies button
    public void acceptCookies() {
        acceptCookiesButton.click();
    }

    // Method to click the "Remember me" checkbox
    public void clickRememberMe() {
        rememberMeCheckbox.click();
    }

    // Method to click the "Forgot Password" link
    public void clickForgotPassword() {
        forgotPasswordLink.click();
    }

    // Method to get the error message for the username field
    public String getUsernameErrorMessage() {
        return usernameErrorMessage.getText();
    }

    // Method to get the error message for the password field
    public String getPasswordErrorMessage() {
        return passwordErrorMessage.getText();
    }

    public String getMinPasswordLengthErrorMessage() {
        return minPasswordLengthErrorMessage.getText();
    }

    public String getLoggedInUserName() {
        WebElement userNameElement = wait.until(ExpectedConditions.visibilityOf(userName));
        return userNameElement.getText();
    }

    // Method to close popup if it appears
    public void closePopup() {
        try {
            // Use a short wait to quickly check for the popup
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));

            // Wait for the close button of the popup to be clickable
            WebElement popup = shortWait.until(ExpectedConditions.elementToBeClickable(closeButton));

            // If the popup is displayed, click to close it
            if (popup.isDisplayed()) {
                popup.click();
                System.out.println("Popup closed.");
            }
        } catch (TimeoutException e) {
            // If popup does not appear, continue the test
            System.out.println("Popup not found, continuing with the test.");
        }
    }
}
