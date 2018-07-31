package org.ucl.newton.framework;

import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collection;

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
    private ExperimentTriggerType trigger;

    public void setStorageConfiguration(StorageConfiguration storageConfiguration) {
        this.storageConfiguration = storageConfiguration;
    }

    public void setProcessorPluginId(String processorPluginId) {
        this.processorPluginId = processorPluginId;
    }

    public void addDataSources(int[] dataSourceIds, String[] dataSourceLocs) {
        // should check both arrays are equal length
        dataSources = new ArrayList<>();
        if(dataSourceIds != null && dataSourceLocs != null) {
            for (int i = 0; i < dataSourceIds.length; i++) {
                dataSources.add(new ExperimentDataSource(0, dataSourceIds[i], dataSourceLocs[i]));
            }
        }
    }

    public void setOutputPattern(String outputPattern) {
        this.outputPattern = outputPattern;
    }

    public void addTrigger(String triggerLabel) {
        if(triggerLabel.equals("Manual")){
            trigger = ExperimentTriggerType.Manual;
        } else if(triggerLabel.equals("On data change")) {
            trigger = ExperimentTriggerType.Onchange;
        }
    }

    public ExperimentConfiguration build() {
        Validate.notNull(storageConfiguration);
        Validate.notEmpty(processorPluginId);
        Validate.notNull(dataSources);
        Validate.notEmpty(outputPattern);
        Validate.notNull(trigger);
        return new ExperimentConfiguration(
            storageConfiguration,
            processorPluginId,
            dataSources,
            outputPattern,
            trigger
        );
    }
}
