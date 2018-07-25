package org.ucl.newton.framework;

/**
 * Data transfer object for the {@link Experiment} class
 *
 * @author John Wilkie
 */

public class ExperimentDto {

    private String name;

    private String description;

    private String selectedTriggerValue;

    private String selectedStorageValue;

    private String selectedTypeValue;

    private String selectedNotebookTypeValue;

    private String outputPattern;

    private String[] dataSourceLocs;

    private String[] dataSourceIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSelectedTriggerValue() {
        return selectedTriggerValue;
    }

    public void setSelectedTriggerValue(String selectedTriggerValue) {
        this.selectedTriggerValue = selectedTriggerValue;
    }

    public String getOutputPattern() {
        return outputPattern;
    }

    public void setOutputPattern(String outputPattern) {
        this.outputPattern = outputPattern;
    }

    public String getSelectedStorageValue() {
        return selectedStorageValue;
    }

    public void setSelectedStorageValue(String selectedStorageValue) {
        this.selectedStorageValue = selectedStorageValue;
    }

    public String getSelectedTypeValue() {
        return selectedTypeValue;
    }

    public void setSelectedTypeValue(String selectedTypeValue) {
        this.selectedTypeValue = selectedTypeValue;
    }

    public String getSelectedNotebookTypeValue() {
        return selectedNotebookTypeValue;
    }

    public void setSelectedNotebookTypeValue(String selectedNotebookTypeValue) {
        this.selectedNotebookTypeValue = selectedNotebookTypeValue;
    }

    public String[] getDataSourceIds() {
        return dataSourceIds;
    }

    public void setDataSourceIds(String[] dataSourceIds) {
        this.dataSourceIds = dataSourceIds;
    }

    public String[] getDataSourceLocs() {
        return dataSourceLocs;
    }

    public void setDataSourceLocs(String[] dataSourceLocs) {
        this.dataSourceLocs = dataSourceLocs;
    }
}
