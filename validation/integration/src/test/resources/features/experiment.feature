Feature: Experiments
    This feature allows users to work with experiments, scripted analysis that
    operates on data sources

Background:
  Given the system has the following users:
    | email                             | password      | fullName          |
    | blair.butterworth.17@ucl.ac.uk    | abc123        | Blair Butterworth |
    | xiaolong.chen@ucl.ac.uk           | isalegend     | Xiaolong Chen     |
    | ziad.halabi.17@ucl.ac.uk          | pokerlife     | Ziad Halabi       |
    | john.wilkie.17@ucl.ac.uk          | wilkie4pres   | John Wiklie       |

  And the system has the following projects:
    | identifier    | name              | description       | owner                       | members                           | dataSources   |
    | project-1     | Project Fizzyo    | Project Fizzyo    | john.wilkie.17@ucl.ac.uk    | blair.butterworth.17@ucl.ac.uk    | weather       |
    | project-2     | Cancer Research   | Cancer Research   | john.wilkie.17@ucl.ac.uk    | blair.butterworth.17@ucl.ac.uk    | weather       |

  And the system has the following experiments:
    | identifier    | name          | description           | creator                     | project       |
    | experiment-1  | Experiment 1  | An experiment         | john.wilkie.17@ucl.ac.uk    | project-1     |
    | experiment-2  | Experiment 2  | Another experiment    | john.wilkie.17@ucl.ac.uk    | project-1     |

Scenario: View experiment list
  Given the user logs in as "blair.butterworth.17@ucl.ac.uk" with "abc123" as their password
  When the user browses to the experiments page for project-1
  Then the experiment list should contain the following experiments:
    | identifier    | name          | description           | creator                           | project       |
    | experiment-1  | Experiment 1  | An experiment         | blair.butterworth.17@ucl.ac.uk    | project-1     |
    | experiment-2  | Experiment 2  | Another experiment    | blair.butterworth.17@ucl.ac.uk    | project-1     |

Scenario: View experiment details
  Given the user logs in as "blair.butterworth.17@ucl.ac.uk" with "abc123" as their password
  When the user browses to the experiment details page for experiment-1 (project-1)
  Then the experiment details page should contain the following:
    | identifier    | name          | description           | creator                           | project       |
    | experiment-1  | Experiment 1  | An experiment         | blair.butterworth.17@ucl.ac.uk    | project-1     |

Scenario: Run experiment
  Given the user logs in as "blair.butterworth.17@ucl.ac.uk" with "abc123" as their password
  When the user browses to the experiment details page for experiment-1 (project-1)
  And the user runs the experiment

Scenario: View new experiment page
  Given the user logs in as "blair.butterworth.17@ucl.ac.uk" with "abc123" as their password
  When the user browses to the experiments page for project-1
  And clicks the new experiment button
  Then the user should be on the new experiment page for project-1

Scenario: Create new experiment
  Given the user logs in as "blair.butterworth.17@ucl.ac.uk" with "abc123" as their password
  And the user is on the new experiment page for project-1
  When the user enters the experiment details:
    | identifier    | name          | description           | creator                           | project       |
    | experiment-3  | Experiment 3  | A third experiment    | blair.butterworth.17@ucl.ac.uk    | project-1     |
  And clicks the create experiment button
  Then the experiment list should contain the following experiments:
    | identifier    | name          | description           | creator                           | project       |
    | experiment-1  | Experiment 1  | An experiment         | john.wilkie.17@ucl.ac.uk          | project-1     |
    | experiment-2  | Experiment 2  | Another experiment    | john.wilkie.17@ucl.ac.uk          | project-1     |
    | experiment-3  | Experiment 3  | A third experiment    | blair.butterworth.17@ucl.ac.uk    | project-1     |

Scenario: Create new experiment with empty name
  Given the user logs in as "blair.butterworth.17@ucl.ac.uk" with "abc123" as their password
  And the user is on the new experiment page for project-1
  When the user enters the experiment details:
    | identifier    | name          | description           | creator                           | project       |
    | experiment-3  |               | A third experiment    | blair.butterworth.17@ucl.ac.uk    | project-1     |
  And clicks the create experiment button
  Then the user should be on the new experiment page for project-1

Scenario: Create an experiment with the same name as one already in the system
  Given the user logs in as "blair.butterworth.17@ucl.ac.uk" with "abc123" as their password
  And the user has already created an experiment with name "Test experiment" for project "project-1"
  And the user is on the new experiment page for project-1
  When the user enters the experiment details:
    | identifier      | name            | description           | creator                           | project       |
    | test-experiment | Test experiment | Description           | blair.butterworth.17@ucl.ac.uk    | project-1     |
  And clicks the create experiment button
  Then the user should be on the new experiment page for project-1
  And a warning message should be shown for a duplicate experiment name "Test experiment"

Scenario: Update an experiment
  Given the user logs in as "john.wilkie.17@ucl.ac.uk" with "wilkie4pres" as their password
  And the user is on the experiment settings page for experiment "experiment-1" in project "project-1"
  When the user changes the experiment description to "A new description"
  And presses the experiment update button
  Then the user should be shown a successful update message

Scenario: Delete an experiment as creator
  Given the user logs in as "john.wilkie.17@ucl.ac.uk" with "wilkie4pres" as their password
  And the user is on the experiment settings page for experiment "experiment-1" in project "project-1"
  When the user presses the delete button
  Then the user should be on the experiments page for "project-1"
  And the experiment list should contain the following experiments:
    | identifier    | name          | description           | creator                           | project       |
    | experiment-2  | Experiment 2  | Another experiment    | blair.butterworth.17@ucl.ac.uk    | project-1     |

Scenario: Attempt to delete an experiment when not creator
  Given the user logs in as "blair.butterworth.17@ucl.ac.uk" with "abc123" as their password
  And the user is on the experiment settings page for experiment "experiment-1" in project "project-1"
  Then the delete button should be deactivated