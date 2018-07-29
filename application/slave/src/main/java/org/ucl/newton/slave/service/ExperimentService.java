/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.ucl.newton.bridge.ExecutionCoordinator;
import org.ucl.newton.common.exception.ConnectionException;
import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.slave.application.ApplicationStorage;
import org.ucl.newton.slave.network.MasterServerFactory;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Path;

/**
 * Instances of this class are used to obtain experiment assets.
 *
 * @author Ziad Halabi
 * @author Blair Butterworth
 */
@Service
public class ExperimentService
{
    private ApplicationStorage storage;
    private ExecutionCoordinator master;

    @Inject
    public ExperimentService(ApplicationStorage storage, MasterServerFactory serverFactory) {
        this.master = serverFactory.get();
        this.storage = storage;
    }

    public Path getRepository(String experimentId) throws IOException {
        Path repositoryPath = storage.getTempDirectory().resolve(experimentId + ".zip");
        File repositoryFile = repositoryPath.toFile();

        if (!repositoryFile.exists()) {
            downloadRepository(experimentId, repositoryFile);
        }
        return repositoryPath;
    }

    private void downloadRepository(String experimentId, File destination) throws IOException {
        FileUtils.createNew(destination);
        try(InputStream inputStream = master.getExperimentRepository(experimentId);
            OutputStream outputStream = new FileOutputStream(destination)) {
            IOUtils.copy(inputStream, outputStream);
        }
        catch (Exception cause) {
            throw new ConnectionException(cause);
        }
    }
}
