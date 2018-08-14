/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.framework;

/**
 * Enum used to specify the trigger for running an experiment
 *
 * @author John Wilkie
 */
public enum ExperimentTriggerType
{
    Manual  ("Manual"),
    Onchange("On data change");

    private final String name;

    ExperimentTriggerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
