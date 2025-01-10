package com.practiceautomation.tests.exceptions;

import com.google.common.base.Verify;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionTests {
    private WebDriver driver;
    private Logger logger;

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        logger = Logger.getLogger(ExceptionTests.class.getName());
        logger.setLevel(Level.INFO);
        logger.info("Running test in " + browser);
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                logger.warning("Configuration for " + browser + "is missing,so running tests in Chrome by default");
                driver = new ChromeDriver();
                break;
        }
        // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Open page
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
        logger.info("Browser is closed;");
    }

    @Test
    public void NoSuchElementEXceptionTest() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        addButton.click();

        WebElement row2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

      /*  try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
*/
        // Verify Row 2 input field is displayed
        // WebElement row2 = driver.findElement(By.xpath("//div[@id='row2']/input"));
        Assert.assertTrue(row2.isDisplayed(), "Row 2 input field is not displayed");

    }

    @Test
    public void timeoutExceptionTest() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));

        // Click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        addButton.click();

        WebElement row2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

        Assert.assertTrue(row2.isDisplayed(), "Row 2 input field is not displayed");

    }

    @Test
    public void elementNotInteractabletExceptionTest() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));

        // Click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        addButton.click();

        WebElement row2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
        row2.sendKeys("Sushi");

        // Click Save button
        WebElement saveButton = driver.findElement(By.xpath("//div[@id='row2']/button[@name='Save']"));
        saveButton.click();

        // Verify text saved
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
        String actualMessage = successMessage.getText();
        String expectedMessage = "Row 2 was saved";

        Assert.assertEquals(actualMessage, expectedMessage, "Message is not expected");
    }

    @Test
    public void invalidElementStateExceptionTest() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));

        WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit_btn")));
        editButton.click();

        WebElement row1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row1']/input")));
        row1.clear();
        row1.sendKeys("Sushi");

        // Click Save button
        WebElement saveButton = driver.findElement(By.xpath("//div[@id='row1']/button[@name='Save']"));
        saveButton.click();

        // Verify text saved
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
        String actualMessage = successMessage.getText();
        String expectedMessage = "Row 1 was saved";

        Assert.assertEquals(actualMessage, expectedMessage, "Message is not expected");
    }

    @Test
    public void StaleElementReferenceExceptionTest() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        addButton.click();

        // Verify instruction text element is no longer displayed
        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("instructions"))), "Instructions text is still displayed");

    }
}