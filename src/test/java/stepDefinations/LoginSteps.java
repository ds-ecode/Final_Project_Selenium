// stepDefinations/LoginSteps.java
package stepDefinations;

import org.junit.Assert;

import factory.BaseClass;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.GoogleLoginPage;
import pageObjects.HomePage;
import utilities.ExcelUtils;

public class LoginSteps {

    HomePage hp;
    GoogleLoginPage glp;

    @When("user goes to login and clicks Google")
    public void user_goes_to_login_and_clicks_google() {
        hp = new HomePage(BaseClass.getDriver());
        hp.openLogin();
        hp.clickContinueWithGoogle();
    }

    @When("user attempts Google login with credentials from sheet {string}")
    public void user_attempts_google_login_with_credentials_from_sheet(String sheetName) throws Exception {
        glp = new GoogleLoginPage(BaseClass.getDriver());
        glp.switchToGoogleWindow();

        String credPath = "./testData/Credentials.xlsx"; // or read from config.properties
        String[] creds = ExcelUtils.readCredentials(credPath, sheetName);

        BaseClass.getLogger().info("Attempting Google login with email: " + creds[0]);
        glp.attemptLogin(creds[0], creds[1]);

        boolean errorShown = glp.isErrorVisible();
        String errText = glp.getErrorText();

        if (errorShown) {
            BaseClass.getLogger().error("Google login failed as expected. Error: " + errText);
        } else {
            BaseClass.getLogger().warn("No explicit error detected, but login likely failed/blocked.");
        }

        glp.switchBackToParent();
    }

    @Then("mark the test as failed intentionally after invalid login")
    public void mark_the_test_as_failed_intentionally_after_invalid_login() {
        BaseClass.getLogger().error("Intentionally failing the scenario after invalid login to capture screenshot & logs.");
        Assert.fail("Invalid Google credentials — test failed intentionally as per requirement.");
    }
}