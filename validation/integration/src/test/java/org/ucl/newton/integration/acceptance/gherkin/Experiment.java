/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.acceptance.gherkin;

import org.ucl.newton.integration.acceptance.newton.experiment.ExperimentDto;

/**
 * Contains information about an experiment in the Newton system.
 *
 * @author Blair Butterworth
 */
public class Experiment
{
    private String identifier;
    private String name;
    private String description;
    private String creator;
    private String project;

    public Experiment() {
    }

    public Experiment(
        String identifier,
        String name,
        String description,
        String creator,
        String project)
    {
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.project = project;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCreator() {
        return creator;
    }

    public String getProject() {
        return project;
    }
    
    public ExperimentDto asExperimentDto() {
        return new ExperimentDto(
            identifier,
            name,
            description,
            creator,
            project,
            "Manual",
            "newton-python",
            "Newton",
            "*.csv",
            "*.html",
            new String[0],
            new String[0]);
    }
}
