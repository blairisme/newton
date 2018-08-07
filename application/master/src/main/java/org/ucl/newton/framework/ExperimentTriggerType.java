package org.ucl.newton.framework;

/**
 * Enum used to specify the trigger for running an experiment
 *
 * @author John Wilkie
 */
public enum ExperimentTriggerType {
    Manual ("Manual"),
    Onchange ("On data change");

    private final String name;

    private ExperimentTriggerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /*
    mode1 ("Fancy Mode 1"),
    mode2 ("Fancy Mode 2"),
    mode3 ("Fancy Mode 3");

    private final String name;

    private Modes(String s) {
        name = s;
    }
     */
}
