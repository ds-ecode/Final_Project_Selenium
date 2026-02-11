package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UpcomingBikes extends BasePage
{
	public UpcomingBikes(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath="//h1[normalize-space()='Upcoming Bikes in India']")
	WebElement confirmMsg;
	
	public String getMsg()
	{
		return confirmMsg.getText();
	}
	
}
