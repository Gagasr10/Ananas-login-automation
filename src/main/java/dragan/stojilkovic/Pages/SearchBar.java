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

public class SearchBar {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//input[@id='autocomplete-1-input']")
    WebElement searchBar;

    @FindBy(xpath = "//button[@aria-label='Search']")
    WebElement searchButton;

    @FindBy(xpath = "//button[@aria-label='zatvori popup']")
    WebElement closeButton;

   
    @FindBy(css = "div.ais-Hits-list")
    WebElement searchResults;

    
    public SearchBar(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

   
    public void search(String text) {
        System.out.println("Performing search for: " + text);
        closePopupIfPresent();
        wait.until(ExpectedConditions.visibilityOf(searchBar));
        searchBar.clear();
        searchBar.sendKeys(text);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }

   
    public void closePopupIfPresent() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            WebElement popup = shortWait.until(ExpectedConditions.elementToBeClickable(closeButton));
            if (popup.isDisplayed()) {
                popup.click();
                System.out.println("Popup closed.");
            }
        } catch (TimeoutException e) {
            System.out.println("No popup to close.");
        }
    }

    
    public boolean areResultsDisplayed() {
        try {
            // Sačekaj da se pojavi bar jedan proizvod u listi rezultata
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement firstResult = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("ul.ais-Hits-list li.ais-Hits-item")
            ));
            return firstResult.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}