/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.experiment;

import org.springframework.stereotype.Service;
import org.ucl.newton.framework.Experiment;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Instances of this interface provide access to experiment data.
 *
 * @author Blair Butterworth
 * @author John Wilkie
 */
@Service
public class ExperimentService
{
    private ExperimentRepository repository;

    @Inject
    public ExperimentService(ExperimentRepository repository) {
        this.repository = repository;
    }

    public Experiment addExperiment(Experiment experiment) {
        return repository.addExperiment(experiment);
    }

    public Collection<Experiment> getExperimentsByParentProjectName(String projectName) {
        return repository.getExperimentsForProject(projectName);
    }

    public Experiment getExperimentById(int experimentId) {
        return repository.getExperimentById(experimentId);
    }

    public Experiment getExperimentByIdentifier(String identifier) {
        return repository.getExperimentByIdentifier(identifier);
    }

    public void update(Experiment experiment) {
        repository.update(experiment);
    }
}
