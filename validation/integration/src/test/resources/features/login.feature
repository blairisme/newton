Feature: Login
    This feature allows users to access the system by providing authentication
    information.

Background:
  Given the system has the following users:
    | email                             | password      | fullName          |
    | blair.butterworth.17@ucl.ac.uk    | abc123        | Blair Butterworth |
    | xiaolong.chen@ucl.ac.uk           | isalegend     | Xiaolong Chen     |
    | ziad.halabi.17@ucl.ac.uk          | pokerlife     | Ziad Halabi       |
    | john.wilkie.17@ucl.ac.uk          | wilkie4pres   | John Wiklie       |

Scenario: Correct authentication details
   When the user is shown the login page
   And the user enters "blair.butterworth.17@ucl.ac.uk" as their username and "abc123" as their password
   And the user selects the signin option
   Then the user should be shown the project list

Scenario: Incorrect user
   When the user is shown the login page
   And the user enters "batman" as their username and "isalegend" as their password
   And the user selects the signin option
   Then the user should be shown an authentication error

Scenario: Incorrect password
  When the user is shown the login page
  And the user enters "xiaolong.chen@ucl.ac.uk" as their username and "completelywrong" as their password
  And the user selects the signin option
  Then the user should be shown an authentication error