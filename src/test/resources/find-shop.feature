Feature: Shop Manager Wants to find a nearest shop using Geo Locator
  Scenario: Shop Manager wants to find a nearest shop
    When the shop Manager adds  /shop method
    | BIS Balham    | 1 | SW17  |
    | BIS Heathrow  | 3 | TW6   |
    | BIS Croydon   | 2 | CR0   |
    When the shop Manager finds /shop/find method
     | 51.1424544 | -0.0642075 |
    And  it receives nearest shop
