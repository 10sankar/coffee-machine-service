Feature: user wants a coffee

  Scenario: user checks available beverages
    When user checks beverages
    Then machine should list below
      | coffee           |
      | cream-coffee     |
      | latte            |
      | cappuccino       |
      | low-beverage     |
      | partial-beverage |


  Scenario: user selects coffee, which has all required ingredients
    When user picks "coffee"
    Then machine should respond "Enjoy the drink"

  Scenario: user selects tea, which isn't listed
    When user picks "tea"
    Then machine should respond "unknown item"

  Scenario: user selects low-beverage, which has all required ingredients
    When user picks "low-beverage"
    Then machine should respond "Insufficient ingredient"

  Scenario: user wants to check on ingredients
    When user want to see running low ingredients
    Then machine should list below
      | low-ingredient1 |
      | low-ingredient2 |

  Scenario: user wants to fill on ingredients
    When user want to fill below ingredients
      | low-ingredient1 |
      | low-ingredient2 |
    Then machine should respond "successfully filled"

  Scenario: user selects low-beverage, which has been refilled
    When user picks "low-beverage"
    Then machine should respond "Enjoy the drink"
