Feature: Google login via ZigWheels

  Scenario: Attempt Google login with invalid credentials (intentionally fail)
    Given User navigates to Home Page
    When user goes to login and clicks Google
    And user attempts Google login with credentials from sheet "Google"
    Then mark the test as failed intentionally after invalid login