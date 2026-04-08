package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class SearchTest extends BaseTest {

    @Test
    public void testValidSearch() {
        searchBar.search("Laptop");
        Assert.assertTrue(searchBar.areResultsDisplayed(), "Search results should be displayed for valid query.");
    }

    @Test
    public void testEmptySearch() {
        searchBar.search("");
        Assert.assertFalse(searchBar.areResultsDisplayed(), "Search results should NOT be displayed for empty input.");
    }

    @Test
    public void testInvalidSearch() {
        searchBar.search("asdkfhalksdjfh");
        Assert.assertFalse(searchBar.areResultsDisplayed(), "Search results should NOT be displayed for invalid input.");
    }
}