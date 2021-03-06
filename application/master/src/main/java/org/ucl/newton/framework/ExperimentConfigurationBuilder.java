/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.framework;

import org.apache.commons.lang3.Validate;
import org.ucl.newton.sdk.processor.DataProcessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Instances of this class construct {@link ExperimentConfiguration} instances.
 *
 * @author John Wilkie
 */
public class ExperimentConfigurationBuilder {

    private StorageConfiguration storageConfiguration;
    private String processorPluginId;
    private Collection<ExperimentDataSource> dataSources;
    private String outputPattern;
    private String displayPattern;
    private ExperimentTriggerType trigger;

    public ExperimentConfigurationBuilder() {
        outputPattern = "";
        displayPattern = "";
        dataSources = Collections.emptyList();
    }

    public ExperimentConfigurationBuilder setStorageConfiguration(StorageConfiguration storageConfiguration) {
        this.storageConfiguration = storageConfiguration;
        return this;
    }

    public ExperimentConfigurationBuilder setProcessorPluginId(String processorId, Collection<DataProcessor> processors) {
        for (DataProcessor processor: processors) {
            if (Objects.equals(processor.getIdentifier(), processorId)) {
                processorPluginId = processor.getIdentifier();
            }
            else if (Objects.equals(processor.getVisualization().getName(), processorId)) {
                processorPluginId = processor.getIdentifier();
            }
        }
        return this;
    }

    public ExperimentConfigurationBuilder setProcessorPluginId(String processorPluginId) {
        this.processorPluginId = processorPluginId;
        return this;
    }

    public ExperimentConfigurationBuilder addDataSources(String[] dataSourceIds, String[] dataSourceLocs) {
        dataSources = new ArrayList<>();
        if (dataSourceIds != null && dataSourceLocs != null) {
            Validate.isTrue(dataSourceIds.length == dataSourceLocs.length);

            for (int i = 0; i < dataSourceIds.length; i++) {
                dataSources.add(new ExperimentDataSource(0, dataSourceIds[i], dataSourceLocs[i]));
            }
        }
        return this;
    }

    public ExperimentConfigurationBuilder setOutputPattern(String outputPattern) {
        this.outputPattern = outputPattern;
        return this;
    }

    public ExperimentConfigurationBuilder setDisplayPattern(String displayPattern) {
        this.displayPattern = displayPattern;
        return this;
    }

    public ExperimentConfigurationBuilder addTrigger(String triggerLabel) {
        if(Objects.equals(triggerLabel, "Manual")){
            trigger = ExperimentTriggerType.Manual;
        } else if(Objects.equals(triggerLabel, "On data change")) {
            trigger = ExperimentTriggerType.Onchange;
        }
        return this;
    }

    public ExperimentConfiguration build() {
        Validate.notNull(storageConfiguration);
        Validate.notEmpty(processorPluginId);
        Validate.notNull(dataSources);
        Validate.notNull(displayPattern);
        Validate.notNull(outputPattern);
        Validate.notNull(trigger);
        return new ExperimentConfiguration(
            storageConfiguration,
            processorPluginId,
            dataSources,
            outputPattern,
            displayPattern,
            trigger
        );
    }
}
