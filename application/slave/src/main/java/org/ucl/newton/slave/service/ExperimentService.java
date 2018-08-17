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
import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.common.archive.ZipUtils;
import org.ucl.newton.common.exception.ConnectionException;

import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.slave.application.ApplicationStorage;
import org.ucl.newton.slave.engine.RequestContext;
import org.ucl.newton.slave.engine.RequestLogger;
import org.ucl.newton.slave.engine.RequestWorkspace;
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

    public void addRepository(ExecutionRequest request, RequestContext context) throws IOException {
        RequestLogger logger = context.getLogger();
        RequestWorkspace workspace = context.getWorkspace();

        logger.info("Obtaining experiment repository");
        Path repositoryArchive = getRepository(request, logger);

        logger.info(" - Copying repository to workspace");
        ZipUtils.unzip(repositoryArchive, workspace.getRepository());
    }

    private Path getRepository(ExecutionRequest request, RequestLogger logger) throws IOException {
        String experimentId = request.getExperiment();
        Path repositoryPath = storage.getTempDirectory().resolve(experimentId + ".zip");
        File repositoryFile = repositoryPath.toFile();

        if (repositoryFile.exists()) {
            logger.info(" - Updating experiment repository cache");
            FileUtils.delete(repositoryFile);
        }
        logger.info(" - Downloading experiment repository");
        downloadRepository(experimentId, repositoryFile, logger);

        return repositoryPath;
    }

    private void downloadRepository(String experimentId, File destination, RequestLogger logger) throws IOException {
        FileUtils.createNew(destination);
        try(InputStream inputStream = master.getExperimentRepository(experimentId);
            OutputStream outputStream = new FileOutputStream(destination)) {
            IOUtils.copy(inputStream, outputStream);
        }
        catch (Exception cause) {
            logger.error(" - Failed to download experiment repository " + experimentId, cause);
            throw new ConnectionException(cause);
        }
    }
}
