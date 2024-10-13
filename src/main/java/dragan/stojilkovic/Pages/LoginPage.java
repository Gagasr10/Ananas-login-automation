package dragan.stojilkovic.Pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    WebDriver driver;

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
    WebElement errorMessag;  // 
    
  // XPath selector for the error message for the username field
    @FindBy(xpath = "(//p[contains(@class, 'Mui-error')])[1]")
    private WebElement usernameErrorMessage;

    // XPath selector for the error message for the password field
    @FindBy(xpath = "(//p[contains(@class, 'Mui-error')])[2]")
    private WebElement passwordErrorMessage;
    
    @FindBy(xpath = "//p[contains(text(), 'Minimum 8 karaktera.')]")
	public WebElement minPasswordLengthErrorMessage;

    @FindBy(xpath = "//span[@class='sc-79pcpx-0 ieekjL']")
    WebElement userName;

    
    
   

    
    // Constructor for the LoginPage class that initializes web elements using PageFactory
    public LoginPage(WebDriver driver) {
        this.driver = driver;
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
    
    // Combined method for loogin
    public void login(String user, String pass) {
        setUsername(user);
        setPassword(pass);
        clickLogin();
    }

    // Method to retrieve the error message displayed after a failed login attempt
    public String getErrorMessage() {
    	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement userNameElement = wait.until(ExpectedConditions.visibilityOf(userName));
        return userNameElement.getText();
    }


    public WebElement getUserNameElement() {
        return userName;
    }

   

    

    
    
//    public String getLoginErrorMessage() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOf(loginErrorMessage));  // Wait until the login error message is visible
//        return loginErrorMessage.getText();
//    }
    
    
    
    
}
