@smoketest
Feature: Testing Gmail functionality
  @smoketest
  Scenario: Testing Compose Functionality Scenarios
    Given Setup Browser
    And User is navigated to the Gmail
    And User input the valid userName and password
    And User should be on the Gmail inbox page
    When User clicks on compose button
    And User fills in the email details with subject and body
    Then User clicks on send button
    And Close the Browser
