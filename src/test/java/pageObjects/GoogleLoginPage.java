// pageObjects/GoogleLoginPage.java
package pageObjects;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;

import factory.BaseClass;

public class GoogleLoginPage extends BasePage {

    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    private String parentHandle;

    public GoogleLoginPage(WebDriver driver) {
        super(driver);
    }

    // Google selectors
    @FindBy(id = "identifierId")
    WebElement emailInput;

    @FindBy(xpath = "//input[@name='Passwd' or @type='password']")
    WebElement passwordInput;

    @FindBy(xpath = "//span[text()='Next']/ancestor::button[not(@disabled)]")
    WebElement nextBtn;

    // Common error containers on Google sign-in
    private By genericError = By.xpath(
            "//div[@role='alert']//div[contains(@class,'o6cuMc')] | " +
                    "//div[contains(text(),'Wrong password') or contains(text(),'Couldn’t find your Google Account') " +
                    "or contains(text(),'Enter a valid email') or contains(text(),'Try again')]"
    );

    public void switchToGoogleWindow() {
        parentHandle = driver.getWindowHandle();
        BaseClass.getLogger().info("Waiting for Google login window to open...");

        // Wait for a new window and switch
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(d -> driver.getWindowHandles().size() > 1);
        Set<String> handles = driver.getWindowHandles();
        for (String h : handles) {
            if (!h.equals(parentHandle)) {
                driver.switchTo().window(h);
                break;
            }
        }

        BaseClass.getLogger().info("Switched to Google window. Title: " + driver.getTitle());
        // Sometimes Google presents "This browser or app may not be secure."
        // We just proceed — the test is meant to fail anyway.
    }

    public void attemptLogin(String email, String password) {
        BaseClass.getLogger().info("Entering email on Google...");
        wait.until(ExpectedConditions.visibilityOf(emailInput)).clear();
        emailInput.sendKeys(email);
        nextBtn.click();

        // If Google blocks, password may not come; we still try
        try {
            BaseClass.getLogger().info("Entering password on Google...");
            wait.until(ExpectedConditions.visibilityOf(passwordInput)).clear();
            passwordInput.sendKeys(password);
            nextBtn.click();
        } catch (TimeoutException te) {
            BaseClass.getLogger().warn("Password field not visible. Google may have blocked automation or email invalid.");
        }
    }

    public boolean isErrorVisible() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(genericError),
                    // Fallback: still on accounts.google.com and not redirected back
                    d -> driver.getCurrentUrl().contains("accounts.google.com")
            ));
            return true;
        } catch (TimeoutException te) {
            return false;
        }
    }

    public String getErrorText() {
        try {
            return driver.findElement(genericError).getText().trim();
        } catch (NoSuchElementException e) {
            return "(No explicit error text captured)";
        }
    }

    public void switchBackToParent() {
        if (parentHandle != null) {
            driver.switchTo().window(parentHandle);
        }
    }
}