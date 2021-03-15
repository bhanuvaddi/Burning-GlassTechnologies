@BBCLocalWeather @SmokeTests
Feature: BBC Local Weather
  As a user
  I navigate to BBC weather page
  I can view the local weather

  Scenario Outline: Navigate to BBC home page to view headlines
    Given User navigate to BBC home page
    When User select local weather
    And User supply the postcode or city name as "<search item>"
    Then User can view local weather based on "<search item>"
    Examples:
      | search item |
      | GU14 9DP    |
      | FARNBOROUGH |