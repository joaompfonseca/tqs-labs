Feature: BlazeDemo

  Scenario: Buying a trip
    When I navigate to 'https://blazedemo.com'
    And I choose a trip from 'Boston' to 'New York'
    And I click find flights
    Then I should see the list of flights from 'Boston' to 'New York'

    When I choose the flight in row 1
    Then I should see the purchase form page

    When I fill in the form with
      | key              | value                  |
      | inputName        | João Fonseca           |
      | address          | Universidade de Aveiro |
      | city             | Aveiro                 |
      | state            | Aveiro                 |
      | zipCode          | 12345                  |
      | cardType         | Diner's Club           |
      | creditCardNumber | 12345                  |
      | creditCardMonth  | 3                      |
      | creditCardYear   | 2023                   |
      | nameOnCard       | João Fonseca           |
      | rememberMe       | True                   |
    Then I should see the remember me checkbox checked

    When I click purchase flight
    Then I should see the confirmation page
    And I should see 'BlazeDemo Confirmation' in the title