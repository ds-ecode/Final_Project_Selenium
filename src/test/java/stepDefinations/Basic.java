package stepDefinations;

import org.junit.Assert;
import factory.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.HomePage;
import pageObjects.UpcomingBikes;

public class Basic {
    
    HomePage hp;
    UpcomingBikes ub;

    @Given("User navigates to Home Page")
    public void user_navigates_to_home_page() {
        System.out.println("Browser is open and at Home Page");
    }

    @When("user goes to New Bikes and Clicks Upcoming Bikes")
    public void user_goes_to_new_bikes_and_clicks_upcoming_bikes() {
        hp = new HomePage(BaseClass.getDriver());
        hp.selectUpcomingBikes(); 
    }

    @Then("user sees the upcoming bikes page")
    public void user_sees_the_upcoming_bikes_page() {
        ub = new UpcomingBikes(BaseClass.getDriver());
        Assert.assertEquals("Upcoming Bikes in India", ub.getMsg());
    }
}