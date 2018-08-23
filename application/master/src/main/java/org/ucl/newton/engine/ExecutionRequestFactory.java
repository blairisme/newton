/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.engine;

import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.bridge.ExecutionRequestBuilder;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.ExperimentConfiguration;
import org.ucl.newton.framework.ExperimentDataSource;
import org.ucl.newton.framework.StorageConfiguration;

import java.util.Collection;

/**
 * Instances of this class build {@link ExecutionRequest ExecutionRequests}
 * from {@link Experiment Experiments}.
 *
 * @author Blair Butterworth
 */
public class ExecutionRequestFactory extends ExecutionRequestBuilder
{
    public ExecutionRequestFactory setExperiment(Experiment experiment) {
        setExperiment(experiment.getIdentifier());
        setVersion(Integer.toString((int)(Math.random()*10000000)));

        ExperimentConfiguration configuration = experiment.getConfiguration();
        setProcessor(configuration.getProcessorPluginId());
        setOutput(configuration.getOutputPattern());

        StorageConfiguration storage = configuration.getStorageConfiguration();
        setScript(storage.getNameOfInitialScript());

        Collection<ExperimentDataSource> dataSources = configuration.getExperimentDataSources();
        for (ExperimentDataSource dataSource: dataSources) {
            addDataSource(dataSource.getDataSourceId());
        }
        return this;
    }
}
