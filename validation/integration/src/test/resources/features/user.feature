Feature: Users
    This feature relates to users profiles within the system

Background:
  Given the system has the following users:
    | email                             | password      | fullName          |
    | blair.butterworth.17@ucl.ac.uk    | abc123        | Blair Butterworth |
    | john.wilkie.17@ucl.ac.uk          | wilkie4pres   | John Wilkie       |

  And the system has the following projects:
    | identifier    | name              | description       | owner                       | members                           | dataSources   |
    | project-1     | Project Fizzyo    | Project Fizzyo    | john.wilkie.17@ucl.ac.uk    | blair.butterworth.17@ucl.ac.uk    | weather       |
    | project-2     | Cancer Research   | Cancer Research   | john.wilkie.17@ucl.ac.uk    | blair.butterworth.17@ucl.ac.uk    | weather       |

  And the system has the following experiments:
    | identifier    | name          | description           | creator                     | project       |
    | experiment-1  | Experiment 1  | An experiment         | john.wilkie.17@ucl.ac.uk    | project-1     |
    | experiment-2  | Experiment 2  | Another experiment    | john.wilkie.17@ucl.ac.uk    | project-1     |

  And the user logs in as "blair.butterworth.17@ucl.ac.uk" with "abc123" as their password

Scenario: User who owns projects checks their profile page
  Given the user logs in as "john.wilkie.17@ucl.ac.uk" with "wilkie4pres" as their password
  When the user navigates to their profile page
  Then they should be on the profile page with details name "John Wilkie", email "john.wilkie.17@ucl.ac.uk", they own "2" projects and are a member of "2" projects

Scenario: User who does not own projects checks their profile page
  Given the user logs in as "blair.butterworth.17@ucl.ac.uk" with "abc123" as their password
  When the user navigates to their profile page
  Then they should be on the profile page with details name "Blair Butterworth", email "blair.butterworth.17@ucl.ac.uk", they own "0" projects and are a member of "2" projects

Scenario: User deletes themselves
  Given the user logs in as "john.wilkie.17@ucl.ac.uk" with "wilkie4pres" as their password
  When the user navigates to their profile page
  And presses the delete button
  Then the user should be deleted and on the landing page
