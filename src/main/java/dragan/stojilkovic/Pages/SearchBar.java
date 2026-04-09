package dragan.stojilkovic.Pages;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object for the search bar component.
 */
public class SearchBar {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(SearchBar.class);

    @FindBy(xpath = "//input[@id='autocomplete-1-input']")
    private WebElement searchInput;

    @FindBy(xpath = "//button[@aria-label='Search']")
    private WebElement searchButton;

    @FindBy(xpath = "//button[@aria-label='zatvori popup']")
    private WebElement closeButton;

    /**
     * Constructor.
     * @param driver WebDriver instance
     * @param wait Pre‑configured WebDriverWait
     */
    public SearchBar(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    /**
     * Performs a search with the given text.
     * @param text search query
     */
    public void search(String text) {
        log.info("Searching for: {}", text);
        closePopupIfPresent();
        wait.until(ExpectedConditions.visibilityOf(searchInput)).clear();
        searchInput.sendKeys(text);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    /** Closes any popup that might block the search, if present. */
    private void closePopupIfPresent() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            WebElement popup = shortWait.until(ExpectedConditions.elementToBeClickable(closeButton));
            if (popup.isDisplayed()) {
                popup.click();
                log.debug("Closed popup");
            }
        } catch (TimeoutException e) {
            log.debug("No popup present");
        }
    }

    /**
     * Checks if search results are displayed.
     * Waits for at least one result item to appear in the DOM.
     * @return true if results are present, false otherwise
     */
    public boolean areResultsDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("ul.ais-Hits-list li.ais-Hits-item")));
            return true;
        } catch (TimeoutException e) {
            log.warn("Search results not displayed");
            return false;
        }
    }
}