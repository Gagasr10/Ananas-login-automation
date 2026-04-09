package dragan.stojilkovic.Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//input[@id='username']")
    private WebElement usernameField;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@id='login-submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//p[contains(text(),'Email ili lozinka nisu u redu')]")
    private WebElement loginErrorMessage;

    @FindBy(xpath = "//button[text()='Slažem se']")
    private WebElement acceptCookiesButton;

    @FindBy(xpath = "//button[@aria-label='zatvori popup']")
    private WebElement closeButton;

    @FindBy(xpath = "(//p[contains(@class, 'Mui-error')])[1]")
    private WebElement usernameErrorMessage;

    @FindBy(xpath = "(//p[contains(@class, 'Mui-error')])[2]")
    private WebElement passwordErrorMessageElement;

    @FindBy(xpath = "//p[contains(text(), 'Minimum 8 karaktera.')]")
    private WebElement minPasswordLengthErrorMessage;

    @FindBy(xpath = "//span[@class='sc-79pcpx-0 ieekjL']")
    private WebElement loggedInUserName;

    @FindBy(xpath = "//a[span[text()='Zaboravili ste lozinku?']]")
    private WebElement forgotPasswordLink;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void setUsername(String user) {
        usernameField.sendKeys(user);
    }

    public void setPassword(String pass) {
        passwordField.sendKeys(pass);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void login(String user, String pass) {
        setUsername(user);
        setPassword(pass);
        clickLogin();
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(loginErrorMessage));
        return loginErrorMessage.getText();
    }

    public void acceptCookies() {
        acceptCookiesButton.click();
    }

    public void clickForgotPassword() {
        forgotPasswordLink.click();
    }

    public String getUsernameErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(usernameErrorMessage));
        return usernameErrorMessage.getText();
    }

    public String getPasswordErrorMessage() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement error = shortWait.until(ExpectedConditions.visibilityOf(passwordErrorMessageElement));
            return error.getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

    public String getMinPasswordLengthErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(minPasswordLengthErrorMessage));
        return minPasswordLengthErrorMessage.getText();
    }

    public String getLoggedInUserName() {
        WebElement userNameElement = wait.until(ExpectedConditions.visibilityOf(loggedInUserName));
        return userNameElement.getText();
    }

    public void closePopup() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            WebElement popup = shortWait.until(ExpectedConditions.elementToBeClickable(closeButton));
            if (popup.isDisplayed()) {
                popup.click();
                System.out.println("Popup closed.");
            }
        } catch (TimeoutException e) {
            System.out.println("Popup not found, continuing with the test.");
        }
    }
}