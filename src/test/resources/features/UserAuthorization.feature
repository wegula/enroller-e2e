Feature: Authorizing users

  Scenario: User logging into system with correct credentials
    Given user goes to login page
    When user types "user" login
    And user types "user" password
    And user clicks "Zaloguj" button
    And user clicks "Wyloguj" element