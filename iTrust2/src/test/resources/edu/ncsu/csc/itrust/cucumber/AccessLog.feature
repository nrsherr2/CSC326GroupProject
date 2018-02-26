#author Nick Sherrill (nrsherr2)

Feature: Access Log
As a user
I want to view a log of my activity
So that I know my account is not compromised.

Scenario: <10 entries
Given I am an authenticated user
And my activity log has 9 entries
When I log in
Then I can view everything in my log

Scenario: >10 entries
Given I am an authenticated user
And my activity log has 11 entries
When I log in
Then I can view the last 10 items in my log

Scenario: The History Page
Given I am an authenticated user
And my activity log has 11 entries
When I log in
And I choose to view my activity log
Then I can view everything in my log

Scenario: Patient Viewable
Given I am logged in as an admin
When I create a user account
Then the user cannot see that action in their history

Scenario: Invalid Date
Given I am an authenticated user
And my activity log has 11 entries
And I choose to view my activity log
When I put in an invalid date
Then there will be an empty log

Scenario: Valid Date
Given I am an authenticated user
And my activity log has 11 entries
And I choose to view my activity log
When I put in a valid date
Then the page shows all activities in that range
