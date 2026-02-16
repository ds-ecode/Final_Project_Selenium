package pageObjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import factory.BaseClass;

public class HomePage extends BasePage
{
	Logger logger = BaseClass.getLogger();
	public HomePage(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath="//span[normalize-space()='NEW BIKES']")
	WebElement newBikes_icon;
	
	@FindBy(linkText ="Upcoming Bikes")
	WebElement upcoming_Bikes;
	
	@FindBy(xpath="//span[normalize-space()='MORE']")
	WebElement more_icon;
	
	@FindBy(linkText ="Used Cars")
	WebElement used_cars;
	
	public void selectUpcomingBikes() 
	{
		
		logger.info("Hovering over 'New Bikes' menu...");
		
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Hover over the main menu
        wait.until(ExpectedConditions.visibilityOf(newBikes_icon));
        actions.moveToElement(newBikes_icon).perform();

        // Wait for dropdown item to be visible and click it
        wait.until(ExpectedConditions.elementToBeClickable(upcoming_Bikes));
        
        logger.info("Clicking on 'Upcoming Bikes' option...");
        upcoming_Bikes.click();
    }
	
	public void selectUsedCars() 
	{
		
		logger.info("Hovering over 'More' menu...");
		
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Hover over the main menu
        wait.until(ExpectedConditions.visibilityOf(more_icon));
        actions.moveToElement(more_icon).perform();

        // Wait for dropdown item to be visible and click it
        wait.until(ExpectedConditions.elementToBeClickable(used_cars));
        
        logger.info("Clicking on 'Used Cars' option...");
        used_cars.click();
    }


    // Google Login Feature 3

    private By loginTrigger = By.xpath(
            "//*[@id='forum_login_div_lg']"//Profile-Icon
    );


    private By googleLoginBtn = By.xpath(
            "//div[@class='lgn-sc c-p txt-l pl-30 pr-30 googleSignIn']"
    );


    public void openLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        logger.info("Opening Login popup/modal...");
        WebElement login = wait.until(ExpectedConditions.elementToBeClickable(loginTrigger));
        login.click();
        logger.info("Login UI opened.");
    }


    public void clickContinueWithGoogle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        logger.info("Clicking 'Continue with Google'...");
        WebElement googleBtn = wait.until(ExpectedConditions.elementToBeClickable(googleLoginBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", googleBtn);
    }



}
