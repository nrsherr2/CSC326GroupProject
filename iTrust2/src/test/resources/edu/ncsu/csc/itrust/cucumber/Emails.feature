#author Nick Sherrill (nrsherr2)

Feature: Email Alerts
As a Patient
I want to receive email alerts
So that I am updated on my account status

Scenario: Password Change Email
Given I am an authenticated user
When I change my password
Then I get an email

Scenario: Appointment Request Email
Given I am an authenticated user
When I log in as hcp
And I navigate to the View Requests page
And I approve the Appointment Request
Then I get an email

Scenario: Locked Out Email
Given I am an authenticated user
When I fail to enter the correct password 3 times
Then I get an email

Scenario: No Email Address
Given I have no valid email
When I fail to enter the correct password 3 times
Then an error is recorded in the access log
