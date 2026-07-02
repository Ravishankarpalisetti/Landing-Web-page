import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class HomepageInteractionTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String BASE_URL = "https://ravitechnoforge.com/";
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(30);

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, WAIT_TIMEOUT);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * TC-HOMEPAGE-001
     * Test Scenario: Initial Application Load and Content Display
     * Test case name: Verify successful navigation to the application homepage
     * Priority: High
     * Comments/Notes: smoke test
     */
    @Test(description = "TC-HOMEPAGE-001: Verify successful navigation to the application homepage")
    public void testHomepageLoadsSuccessfully() {

        // Step 1: Navigate to the application homepage
        driver.get(BASE_URL);

        // Assertion 1: Verify the page title is not empty, confirming the page loaded
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("")));
        String pageTitle = driver.getTitle();
        Assert.assertNotNull(pageTitle, "Page title should not be null after navigation.");
        Assert.assertFalse(pageTitle.isEmpty(), "Page title should not be empty after navigation.");

        // Assertion 2: Verify the current URL matches the expected base URL
        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl, "Current URL should not be null.");
        Assert.assertTrue(
                currentUrl.contains("ravitechnoforge.com"),
                "Current URL should contain 'ravitechnoforge.com'. Actual URL: " + currentUrl
        );

        // Assertion 3: Verify the embedded Google Maps iframe is present on the homepage
        // This confirms the homepage content including the embedded map is displayed
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
        WebElement mapIframe = driver.findElement(By.tagName("iframe"));
        Assert.assertNotNull(mapIframe, "Embedded map iframe should be present on the homepage.");
        Assert.assertTrue(mapIframe.isDisplayed(), "Embedded map iframe should be visible on the homepage.");

        // Assertion 4: Verify the 'Read More' link is present and visible on the homepage
        // Using bestLocator: "center > a.btn"
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("center > a.btn")));
        WebElement readMoreLink = driver.findElement(By.cssSelector("center > a.btn"));
        Assert.assertNotNull(readMoreLink, "Read More link should be present on the homepage.");
        Assert.assertTrue(readMoreLink.isDisplayed(), "Read More link should be visible on the homepage.");
        Assert.assertTrue(
                readMoreLink.getText().trim().equalsIgnoreCase("Read More"),
                "Read More link text should be 'Read More'. Actual: " + readMoreLink.getText()
        );

        // Assertion 5: Verify the page body is rendered (not blank)
        WebElement body = driver.findElement(By.tagName("body"));
        Assert.assertNotNull(body, "Page body element should be present.");
        String bodyText = body.getText();
        Assert.assertFalse(bodyText == null || bodyText.trim().isEmpty(),
                "Page body should contain visible text content.");
    }
}