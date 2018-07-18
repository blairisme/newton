Feature: Login
    This feature allows users to access the system by providing authentication
    information.

Background:
  Given the system has the following users:
    | id        | username      | password      |
    | 1         | blair         | abc123        |
    | 2         | xiaolong      | isalegend     |
    | 3         | ziad          | pokerlife     |
    | 4         | john          | wilkie4pres   |

Scenario: Correct authentication details
   When the user is shown the login page
   And the user enters "admin" as their username and "password" as their password
   And the user selects the signin option
   Then the user should be shown the project list

Scenario: Incorrect user
   When the user is shown the login page
   And the user enters "batman" as their username and "isalegend" as their password
   And the user selects the signin option
   Then the user should be shown an authentication error


Scenario: Incorrect password
  When the user is shown the login page
  And the user enters "xiaolong" as their username and "completelywrong" as their password
  And the user selects the signin option
  Then the user should be shown an authentication error