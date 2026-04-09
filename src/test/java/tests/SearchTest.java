package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class SearchTest extends BaseTest {

    private void goToHomepage() {
        driver.get("https://ananas.rs");
        // Wait for page to be fully loaded
        wait.until(webDriver -> ((org.openqa.selenium.JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    @Test
    public void testValidSearch() {
        goToHomepage();
        searchBar.search("Laptop");
        Assert.assertTrue(searchBar.areResultsDisplayed(), "Search results should be displayed for valid query.");
    }

    @Test
    public void testEmptySearch() {
        goToHomepage();
        searchBar.search("");
        Assert.assertFalse(searchBar.areResultsDisplayed(), "Search results should NOT be displayed for empty input.");
    }

    @Test
    public void testInvalidSearch() {
        goToHomepage();
        searchBar.search("asdkfhalksdjfh");
        Assert.assertFalse(searchBar.areResultsDisplayed(), "Search results should NOT be displayed for invalid input.");
    }
}