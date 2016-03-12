#Scenarios
#### Login
Given that I have an account registered
And I open the app to the Login Page
When I enter in my email username and password
And I press the login button
Then the stats previously set should be remembered.
#### Create Task
Given I am signed in on a valid account  
And I am on the task page  
When I click on the plus button at the bottom  
Then a create task form pops up  
When I complete the form with the required information and press confirm  
Then the pop-up should close  
And a task card with the information I typed in should appear on the task page

#### Edit Task
Given I am signed in on a valid account  
And I am on the task page  
And there is at least one task card  
When I click on the main body of a task card  
Then an edit task form pops up with the task card's information  
When I change a field in the form and press confirm  
Then the pop-up should close
And the task card should be updated with the information just amended

#### Complete Task
Given I am signed in on a valid account  
And I am on the task page  
And there is at least one task card  
When I click complete on a task card  
Then a confirmation dialog pops up  
When I click confirm on the dialog  
Then the dialog should close  
And the task card should be removed from the task page  
When I open the navigation drawer and press stats  
Then the stats activity opens  
And the rating bar for the category of the task I just completed shows my new experience
