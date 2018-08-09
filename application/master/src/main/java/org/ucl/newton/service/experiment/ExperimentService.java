/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.experiment;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.ExperimentDataSource;
import org.ucl.newton.framework.Project;
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
        Validate.notNull(experiment);
        return repository.addExperiment(experiment);
    }

    public Experiment getExperimentById(int experimentId) {
        return repository.getExperimentById(experimentId);
    }

    public Experiment getExperimentByIdentifier(String identifier) {
        Validate.notNull(identifier);
        return repository.getExperimentByIdentifier(identifier);
    }

    public Collection<Experiment> getExperimentsByProject(Project project) {
        Validate.notNull(project);
        return getExperimentsByProject(project.getIdentifier());
    }

    public Collection<Experiment> getExperimentsByProject(String identifier) {
        Validate.notNull(identifier);
        return repository.getExperimentsForProject(identifier);
    }

    public Collection<Experiment> getExperimentsByDataSource(DataSource dataSource) {
        Validate.notNull(dataSource);
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

    public void removeExperiment(Experiment experiment) {
        Validate.notNull(experiment);
        repository.removeExperiment(experiment);
    }

    public void updateExperiment(Experiment experiment) {
        Validate.notNull(experiment);
        repository.update(experiment);
    }
}
