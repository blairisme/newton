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
    | identifier      | name              | description       | owner                       | members                           | dataSources   |
    | project-fizzyo  | Project Fizzyo    | Project Fizzyo    | john.wilkie.17@ucl.ac.uk    | blair.butterworth.17@ucl.ac.uk    | weather       |
    | cancer-research | Cancer Research   | Cancer Research   | john.wilkie.17@ucl.ac.uk    | blair.butterworth.17@ucl.ac.uk    | weather       |

Scenario: View project list
  Given the user logs in as "blair.butterworth.17@ucl.ac.uk" with "abc123" as their password
  When the user browses to the projects page
  Then the project list should contain the following projects:
    | identifier      | name              | description       | owner                       | members                           | dataSources   |
    | project-fizzyo  | Project Fizzyo    | Project Fizzyo    | john.wilkie.17@ucl.ac.uk    | blair.butterworth.17@ucl.ac.uk    | weather       |
    | cancer-research | Cancer Research   | Cancer Research   | john.wilkie.17@ucl.ac.uk    | blair.butterworth.17@ucl.ac.uk    | weather       |

Scenario: View empty project list
  Given the user logs in as "ziad.halabi.17@ucl.ac.uk" with "pokerlife" as their password
  When the user browses to the projects page
  Then the project list should contain the following projects:
    | identifier    | name              | description       | owner                       | members                           | dataSources   |

Scenario: View create new project page
  Given the user "ziad.halabi.17@ucl.ac.uk" has role "admin"
  And the user logs in as "ziad.halabi.17@ucl.ac.uk" with "pokerlife" as their password
  When the user browses to the projects page
  And the user clicks the new project button
  Then the user should be on the new project page

Scenario: Create new project
  Given the user logs in as "blair.butterworth.17@ucl.ac.uk" with "abc123" as their password
  And the user is on the new project page
  When the user enters the project details:
    | identifier      | name              | description       | owner                          | members                           | dataSources   |
    | new-project     | New Project       | Project desc      | blair.butterworth.17@ucl.ac.uk | blair.butterworth.17@ucl.ac.uk    |               |
  And the user clicks the create project button
  Then the project list should contain the following projects:
    | identifier      | name              | description       | owner                          | members                           | dataSources   |
    | project-fizzyo  | Project Fizzyo    | Project Fizzyo    | john.wilkie.17@ucl.ac.uk       | blair.butterworth.17@ucl.ac.uk    | weather       |
    | cancer-research | Cancer Research   | Cancer Research   | john.wilkie.17@ucl.ac.uk       | blair.butterworth.17@ucl.ac.uk    | weather       |
    | new-project     | New Project       | Project desc      | blair.butterworth.17@ucl.ac.uk | blair.butterworth.17@ucl.ac.uk    |               |

Scenario: Create project with blank name
  Given the user logs in as "blair.butterworth.17@ucl.ac.uk" with "abc123" as their password
  And the user is on the new project page
  When the user enters the project details:
    | identifier      | name              | description       | owner                          | members                           | dataSources   |
    | new-project     |                   | Project desc      | blair.butterworth.17@ucl.ac.uk | blair.butterworth.17@ucl.ac.uk    |               |
  And the user clicks the create project button
  Then the user should be on the new project page