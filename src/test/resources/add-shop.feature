Feature: Shop Manager can add or replace shop Geo Locator details
  Scenario: Shop Manager wants to add a New Shop
    When the shop Manager adds /shop
    | BIS Heathrow 2 | 4 | TW6 |
    Then it receives OK status code
    And  it receives no shop details as its a new shop being added

  Scenario: Shop Manager wants to add a same Shop again
    When the shop Manager adds again /shop
    | BIS Heathrow 2 | 4 | TW6 |
    | BIS Heathrow 2 | 4 | TW6 |
    Then it receives OK status code
    And  it receives old shop details as its a new shop being added