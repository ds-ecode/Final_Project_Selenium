package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        // The @ symbol tells Cucumber to read the text file for paths, not run the file itself
        features = {"@target/failed_scenarios.txt"},

        // Use the same glue code as your main runner
        glue = {"stepDefinations", "hooks"},

        // output a separate report so you don't overwrite the main one
        plugin = {
                "pretty",
                "html:reports/rerun-report.html",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
)
public class FailedTestRunner {

}