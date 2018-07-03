/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.experiment;

import org.ucl.newton.framework.Experiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 * Instances of this interface provide access to experiment data.
 *
 * @author Blair Butterworth
 * @author John Wilkie
 */
@Named
public class ExperimentService
{
    private ExperimentRepository repository;

    @Inject
    public ExperimentService(ExperimentRepository repository) { this.repository = repository; }

    public Collection<Experiment> getExperiments(String projectName) {
        Collection<Experiment> experiments = repository.getExperimentsForProject(projectName);
        // check experiments not null
        return experiments;
    }
}
