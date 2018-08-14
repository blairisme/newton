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

  And the user logs in as "blair.butterworth.17@ucl.ac.uk" with "abc123" as their password

Scenario: View experiment list
  When the user browses to the experiments page for project-1
  Then the experiment list should contain the following experiments:
    | identifier    | name          | description           | creator                           | project       |
    | experiment-1  | Experiment 1  | An experiment         | blair.butterworth.17@ucl.ac.uk    | project-1     |
    | experiment-2  | Experiment 2  | Another experiment    | blair.butterworth.17@ucl.ac.uk    | project-1     |

Scenario: View experiment details
  When the user browses to the experiment details page for experiment-1 (project-1)
  Then the experiment details page should contain the following:
    | identifier    | name          | description           | creator                           | project       |
    | experiment-1  | Experiment 1  | An experiment         | blair.butterworth.17@ucl.ac.uk    | project-1     |

Scenario: Run experiment
  When the user browses to the experiment details page for experiment-1 (project-1)
  And the user runs the experiment
