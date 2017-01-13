Feature: Features of expression DAO
 
Scenario: Saving an expression
Given A new expression
When I call the save method
Then I should have one expression in the collection