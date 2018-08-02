/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.experiment;

import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.framework.Experiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.Path;

/**
 * Defines and provides access to the layout structure of the experiment
 * directory.
 *
 * @author Blair Butterworth
 */
@Named
public class ExperimentStorage
{
    private static final String REPOSITORY_DIR = "repository";
    private static final String VERSIONS_DIR = "versions";

    private Path root;

    @Inject
    public ExperimentStorage(ApplicationStorage applicationStorage) {
        root = applicationStorage.getExperimentDirectory();
    }

    public Path getStoragePath(Experiment experiment) {
        return getStoragePath(experiment.getIdentifier());
    }

    public Path getStoragePath(String experimentId) {
        return root.resolve(experimentId);
    }

    public Path getRepositoryPath(Experiment experiment) {
        return getRepositoryPath(experiment.getIdentifier());
    }

    public Path getRepositoryPath(String experimentId) {
        return root.resolve(experimentId).resolve(REPOSITORY_DIR);
    }

    public Path getVersionsPath(Experiment experiment) {
        return getVersionsPath(experiment.getIdentifier());
    }

    public Path getVersionsPath(String experimentId) {
        return root.resolve(experimentId).resolve(VERSIONS_DIR);
    }

    public Path getVersionPath(Experiment experiment, int version) {
        return getVersionPath(experiment.getIdentifier(), version);
    }

    public Path getVersionPath(String experimentId, int version) {
        return root.resolve(experimentId).resolve(VERSIONS_DIR).resolve(Integer.toString(version));
    }
}
