/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.experiment;

import com.google.common.base.Strings;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.ucl.newton.api.experiment.ExperimentDto;
import org.ucl.newton.common.identifier.Identifier;
import org.ucl.newton.framework.*;
import org.ucl.newton.service.plugin.PluginService;
import org.ucl.newton.service.project.ProjectService;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Common operations for working with {@link Experiment Experiments}.
 *
 * @author Blair Butterworth
 */
@Named
public class ExperimentOperations
{
    private static Logger logger = LoggerFactory.getLogger(ExperimentOperations.class);

    private UserService userService;
    private ExperimentStorage experimentStorage;
    private ProjectService projectService;
    private PluginService pluginService;

    @Inject
    public ExperimentOperations(
        UserService userService,
        ExperimentStorage experimentStorage,
        ProjectService projectService,
        PluginService pluginService)
    {
        this.userService = userService;
        this.experimentStorage = experimentStorage;
        this.projectService = projectService;
        this.pluginService = pluginService;
    }

    public Experiment createExperiment(ExperimentDto experimentDto) {
        String experimentId = experimentDto.getIdentifier();
        if (Strings.isNullOrEmpty(experimentId)) {
            experimentId = Identifier.create(experimentDto.getName());
        }
        return createExperiment(experimentDto, experimentId);
    }

    public Experiment createExperiment(ExperimentDto experimentDto, String experimentId) {
        ExperimentBuilder builder = new ExperimentBuilder();
        builder.setName(experimentDto.getName());
        builder.setIdentifier(experimentId);
        builder.setDescription(experimentDto.getDescription());
        builder.setCreator(userService.getAuthenticatedUser());
        builder.setProject(projectService.getProjectByIdentifier(experimentDto.getProject(), true));
        builder.setExperimentVersions(new ArrayList<>());
        builder.setConfiguration(createConfiguration(experimentDto, experimentId));
        return builder.build();
    }

    private ExperimentConfiguration createConfiguration(ExperimentDto experimentDto, String experimentId) {
        ExperimentConfigurationBuilder builder = new ExperimentConfigurationBuilder();
        builder.setStorageConfiguration(createStorageConfiguration(experimentDto, experimentId));
        builder.setProcessorPluginId(experimentDto.getProcessor(), pluginService.getDataProcessors());
        builder.addDataSources(experimentDto.getDataSourceIds(), experimentDto.getDataSourceLocs());
        builder.setOutputPattern(experimentDto.getOutputPattern());
        builder.setDisplayPattern(experimentDto.getDisplayPattern());
        builder.addTrigger(experimentDto.getTrigger());
        return builder.build();
    }

    private StorageConfiguration createStorageConfiguration(ExperimentDto experimentDto, String experimentId) {
        StorageType type = StorageType.Newton;
        String location = experimentStorage.getRepositoryPath(experimentId).toString();
        String script = experimentDto.getProcessor().equals("newton-jupyter") ? "main.ipynb" : "main.py";
        return new StorageConfiguration(0, type, location, script);
    }

    public void populateRepository(Experiment experiment) {
        try {
            Resource template = new ClassPathResource("/templates");
            Resource repository = experiment.getConfiguration().getStorageConfiguration().getStorageLocation();
            FileUtils.copyDirectory(template.getFile(), repository.getFile());
        } catch (IOException error) {
            logger.error("Failed to populate repository", error);
        }
    }
}
