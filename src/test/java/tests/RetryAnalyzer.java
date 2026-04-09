package tests;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Implements TestNG retry logic for flaky tests.
 * Automatically retries a failed test up to MAX_RETRY times.
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int MAX_RETRY = 2; // Retry a failed test at most twice

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY) {
            retryCount++;
            return true;  // Retry the test
        }
        return false;     // Give up after max attempts
    }
}