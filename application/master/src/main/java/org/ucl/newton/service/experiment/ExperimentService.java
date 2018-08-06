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
import org.ucl.newton.framework.ExperimentDataSource;
import org.ucl.newton.sdk.data.DataSource;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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

    public Experiment getExperimentById(int experimentId) {
        return repository.getExperimentById(experimentId);
    }

    public Experiment getExperimentByIdentifier(String identifier) {
        return repository.getExperimentByIdentifier(identifier);
    }

    public Collection<Experiment> getExperimentsByParentProjectName(String projectName) {
        return repository.getExperimentsForProject(projectName);
    }

    public Collection<Experiment> getExperimentsByDataSource(DataSource dataSource) {
        Collection<Experiment> result = new ArrayList<>();
        for (Experiment experiment: repository.getExperiments()) {
            for (ExperimentDataSource experimentDataSource: experiment.getConfiguration().getExperimentDataSources()) {
                if (Objects.equals(experimentDataSource.getDataSourceId(), dataSource.getIdentifier())) {
                    result.add(experiment);
                }
            }
        }
        return result;
    }

    public void update(Experiment experiment) {
        repository.update(experiment);
    }
}
