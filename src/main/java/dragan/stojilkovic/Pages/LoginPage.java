package dragan.stojilkovic.Pages;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object Model class for the login page.
 * Contains all web elements and methods to interact with the login form.
 */
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
    private WebElement passwordErrorMessage;

    @FindBy(xpath = "//p[contains(text(), 'Minimum 8 karaktera.')]")
    private WebElement minPasswordLengthErrorMessage;

    @FindBy(xpath = "//span[@class='sc-79pcpx-0 ieekjL']")
    private WebElement loggedInUserName;

    @FindBy(xpath = "//a[span[text()='Zaboravili ste lozinku?']]")
    private WebElement forgotPasswordLink;

    /**
     * Constructor initialises WebDriver, WebDriverWait and PageFactory.
     * @param driver WebDriver instance
     * @param wait Pre‑configured WebDriverWait (from BaseTest)
     */
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
        return driver.findElement(By.xpath("(//p[contains(@class, 'Mui-error')])[1]")).getText();
    }

    public String getPasswordErrorMessage() {
        try {
            return driver.findElement(By.xpath("(//p[contains(@class, 'Mui-error')])[2]")).getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public String getMinPasswordLengthErrorMessage() {
        return minPasswordLengthErrorMessage.getText();
    }

    public String getLoggedInUserName() {
        WebElement userNameElement = wait.until(ExpectedConditions.visibilityOf(loggedInUserName));
        return userNameElement.getText();
    }

    /** Closes the popup if it appears; does nothing otherwise. */
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