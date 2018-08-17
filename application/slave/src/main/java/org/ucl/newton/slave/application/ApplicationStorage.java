/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.application;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.Path;

/**
 * Provides paths to the application storage area.
 *
 * @author Ziad Halabi
 * @author Blair Butterworth
 */
@Named
public class ApplicationStorage
{
    private Path rootDirectory;
    private Path dataDirectory;
    private Path processorDirectory;
    private Path workspaceDirectory;
    private Path tempDirectory;

    @Inject
    public ApplicationStorage(ApplicationPreferences preferences) {
        this(preferences.getApplicationPath());
    }

    public ApplicationStorage(Path rootDirectory) {
        this.rootDirectory = rootDirectory;
        this.dataDirectory = rootDirectory.resolve("data");
        this.processorDirectory = rootDirectory.resolve("processors");
        this.workspaceDirectory = rootDirectory.resolve("workspaces");
        this.tempDirectory = rootDirectory.resolve("temp");
    }
    
    public Path getRootDirectory() {
        return rootDirectory;
    }

    public Path getDataDirectory() {
        return dataDirectory;
    }

    public Path getProcessorDirectory() {
        return processorDirectory;
    }

    public Path getWorkspaceDirectory() {
        return workspaceDirectory;
    }

    public Path getTempDirectory() {
        return tempDirectory;
    }
}
