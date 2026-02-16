// File: src/test/java/stepDefinations/LoginSteps.java
package stepDefinations;

import org.junit.Assert;

import factory.BaseClass;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.GoogleLoginPage;
import pageObjects.HomePage;
import utilities.ExcelUtils;

public class LoginSteps {

    private HomePage hp;
    private GoogleLoginPage glp;

    @When("user goes to login and clicks Google")
    public void user_goes_to_login_and_clicks_google() {
        hp = new HomePage(BaseClass.getDriver());
        BaseClass.getLogger().info("Opening login and clicking Continue with Google...");
        hp.openLogin();
        hp.clickContinueWithGoogle();
    }

    @When("user attempts Google login with credentials from sheet {string}")
    public void user_attempts_google_login_with_credentials_from_sheet(String sheetName) throws Exception {

        glp = new GoogleLoginPage(BaseClass.getDriver());
        glp.switchToGoogleWindow();
//        Thread.sleep(100000000000);
        String credPath = "./testData/Credentials.xlsx";
        String[] creds = ExcelUtils.readCredentials(credPath, sheetName);

        // Basic validation to avoid ArrayIndexOutOfBounds
        if (creds == null || creds.length < 2) {
            BaseClass.getLogger().error("Credentials not found or invalid format in sheet: " + sheetName);
            glp.switchBackToParent();
            Assert.fail("Credentials missing/invalid in Excel sheet: " + sheetName);
            return;
        }

        String email = creds[0];
        String password = creds[1];

        BaseClass.getLogger().info("Attempting Google login with email: " + email);
        if(!glp.attemptLogin(email, password)) {
        	Assert.fail("Automation Blocked");
        };

        boolean errorShown = false;
        String errText = "";

        try {
            errorShown = glp.isErrorVisible();
            if (errorShown) {
                errText = glp.getErrorText();
            }
        } catch (Exception e) {
            BaseClass.getLogger().warn("Could not detect Google error reliably: " + e.getMessage());
        }

        if (errorShown) {
            BaseClass.getLogger().error("Google login failed as expected. Error: " + errText);
        } else {
            BaseClass.getLogger().warn("No explicit error detected; login is still expected to fail/blocked.");
        }

        // Switch back to ZigWheels window
        glp.switchBackToParent();
    }

    @Then("mark the test as failed intentionally after invalid login")
    public void mark_the_test_as_failed_intentionally_after_invalid_login() {
        // This is intentional so that your @After hook captures screenshot/report.
        BaseClass.getLogger().error("Intentionally failing the scenario after invalid login to capture screenshot & logs.");
        Assert.fail("Invalid Google credentials — test failed intentionally as per requirement.");
    }
}