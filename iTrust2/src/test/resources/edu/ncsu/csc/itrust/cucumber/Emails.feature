#author Nick Sherrill (nrsherr2)

Feature: Email Alerts
As a Patient
I want to receive email alerts
So that I am updated on my account status

Scenario: Password Change Email
Given I can log into iTrust as patient with password 123456
When I change my password
Then I get an email

Scenario: Appointment Request Email
Given I can log into iTrust as hcp with password 123456
And An appointment request exists
When I log in as hcp
And I navigate to the View Requests page
And I approve the Appointment Request
Then I get an email

Scenario: Locked Out Email
Given I can log into iTrust as patient with password 123456
When I try to login 3 times as patient with password 111111
Then I get an email

Scenario: No Email Address
Given I have no valid email
When I fail to enter the correct password 3 times
Then an error is recorded in the access log
