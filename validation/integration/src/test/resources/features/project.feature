Feature: Projects
    This feature allows users to work with projects, logical groupings of
    experiments.

Background:
  Given the system has the following users:
    | email                             | password      | fullName          |
    | blair.butterworth.17@ucl.ac.uk    | abc123        | Blair Butterworth |
    | xiaolong.chen@ucl.ac.uk           | isalegend     | Xiaolong Chen     |
    | ziad.halabi.17@ucl.ac.uk          | pokerlife     | Ziad Halabi       |
    | john.wilkie.17@ucl.ac.uk          | wilkie4pres   | John Wiklie       |

  And the system has the following projects:
    | identifier    | name              | description       | owner                       | members                           | dataSources   |
    | experiment-1  | Project Fizzyo    | Project Fizzyo    | john.wilkie.17@ucl.ac.uk    | blair.butterworth.17@ucl.ac.uk    | weather       |
    | experiment-2  | Cancer Research   | Cancer Research   | john.wilkie.17@ucl.ac.uk    | blair.butterworth.17@ucl.ac.uk    | weather       |

  And the user logs in as "blair.butterworth.17@ucl.ac.uk" with "abc123" as their password

Scenario: View project list
  When the user browses to the projects page
  Then the project list should contain the following projects:
    | identifier    | name              | description       | owner                       | members                           | dataSources   |
    | experiment-1  | Project Fizzyo    | Project Fizzyo    | john.wilkie.17@ucl.ac.uk    | blair.butterworth.17@ucl.ac.uk    | weather       |
    | experiment-2  | Cancer Research   | Cancer Research   | john.wilkie.17@ucl.ac.uk    | blair.butterworth.17@ucl.ac.uk    | weather       |
