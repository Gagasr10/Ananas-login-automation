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

public class SearchBar {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(SearchBar.class);

    @FindBy(xpath = "//button[@aria-label='Search']")
    private WebElement searchButton;

    @FindBy(xpath = "//button[@aria-label='zatvori popup']")
    private WebElement closeButton;

    public SearchBar(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void search(String text) {
        log.info("Searching for: {}", text);
        closePopupIfPresent();

        // Try multiple locators for the search input
        WebElement searchInput = findSearchInput();
        searchInput.clear();
        searchInput.sendKeys(text);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    private WebElement findSearchInput() {
        By[] locators = {
            By.cssSelector("input[placeholder*='Unesi pojam']"),
            By.cssSelector("input[type='search']"),
            By.xpath("//input[contains(@id, 'autocomplete')]")
        };
        for (By locator : locators) {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            } catch (TimeoutException e) {
                log.debug("Locator not found: {}", locator);
            }
        }
        throw new RuntimeException("Search input not found with any locator");
    }

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