Feature: Signup
    This feature allows users to sign up to use the system.

Background:
  Given the system has the following users:
    | email                             | password      | fullName          |
    | blair.butterworth.17@ucl.ac.uk    | abc123        | Blair Butterworth |
    | xiaolong.chen@ucl.ac.uk           | isalegend     | Xiaolong Chen     |
    | ziad.halabi.17@ucl.ac.uk          | pokerlife     | Ziad Halabi       |
    | john.wilkie.17@ucl.ac.uk          | wilkie4pres   | John Wiklie       |

  And the user is on the signup page

Scenario: Correct sign up details
  When the user enters "New User" as their full name, "newemail@ucl.ac.uk" as their email, "abc123" as their password and "abc123" as the reentered password
  And presses the register button
  Then the user should be shown the log in page

Scenario: Already a user registered with that email address
  When the user enters "Blair Butterworth" as their full name, "blair.butterworth.17@ucl.ac.uk" as their email, "abc123" as their password and "abc123" as the reentered password
  And presses the register button
  Then the user should still be on the sign up page
  And be shown a warning

Scenario: Passwords don't match
  When the user enters "New User 3" as their full name, "newemail3@ucl.ac.uk" as their email, "abc123" as their password and "123abc" as the reentered password
  Then the register button should be disabled