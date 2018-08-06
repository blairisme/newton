/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.engine;

import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.ExperimentConfiguration;
import org.ucl.newton.framework.ExperimentTriggerType;
import org.ucl.newton.sdk.data.DataProviderObserver;
import org.ucl.newton.sdk.data.DataSource;
import org.ucl.newton.service.data.DataService;
import org.ucl.newton.service.experiment.ExperimentService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Objects;

/**
 * Monitors data sources provided by data providers, running experiments in
 * response to data source updates.
 *
 * @author Blair Butterworth
 */
@Named
public class ExecutionDataObserver implements ExecutionTrigger, DataProviderObserver
{
    private DataService dataService;
    private ExperimentService experimentService;
    private ExecutionController executionController;

    @Inject
    public ExecutionDataObserver(DataService dataService, ExperimentService experimentService) {
        this.dataService = dataService;
        this.dataService.addDataObserver(this);
        this.experimentService = experimentService;
    }

    @Override
    public void setController(ExecutionController executionController) {
        this.executionController = executionController;
    }

    @Override
    public void dataUpdated(DataSource dataSource) {
        if (executionController != null) {
            Collection<Experiment> experiments = experimentService.getExperimentsByDataSource(dataSource);

            for (Experiment experiment : experiments) {
                ExperimentConfiguration configuration = experiment.getConfiguration();

                if (Objects.equals(configuration.getTrigger(), ExperimentTriggerType.Onchange)) {
                    executionController.startExecution(experiment);
                }
            }
        }
    }
}
