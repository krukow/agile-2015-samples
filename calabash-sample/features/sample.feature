Feature: Playing the game
  Scenario: Tapping a letter
    Given I'm on the main page
    And I start playing
    Then an image of a super hero is shown

    When I choose a letter
    Then that letter becomes selected