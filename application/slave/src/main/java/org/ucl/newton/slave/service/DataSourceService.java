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

import static org.apache.commons.io.FileUtils.copyFileToDirectory;

/**
 * Instances of this class manage a local cache of data sources available for
 * experiment computation.
 *
 * @author Ziad Halabi
 * @author Blair Butterworth
 */
@Service
public class DataSourceService
{
    private ApplicationStorage storage;
    private ExecutionCoordinator master;

    @Inject
    public DataSourceService(ApplicationStorage storage, MasterServerFactory serverFactory) {
        this.master = serverFactory.get();
        this.storage = storage;
    }

    public void addDataSources(ExecutionRequest request, RequestContext context) throws IOException {
        RequestLogger logger = context.getLogger();
        RequestWorkspace workspace = context.getWorkspace();

        logger.info("Obtaining data sources");
        for (String dataSourceId: request.getDatasources()) {
            Path dataSource = getDataSource(dataSourceId, logger);

            logger.info(" - Copying data source to repository");
            copyFileToDirectory(dataSource.toFile(), workspace.getData().toFile());
        }
    }

    private Path getDataSource(String dataSourceId, RequestLogger logger) throws IOException {
        logger.info(" - Experiment depends on data source: " + dataSourceId);

        Path dataSourcePath = storage.getDataDirectory().resolve(dataSourceId);
        File dataSourceFile = dataSourcePath.toFile();

        if (!dataSourceFile.exists()) {
            logger.info(" - Downloading data source (" + dataSourceId + ")");
            downloadDataSource(dataSourceId, dataSourceFile, logger);
        }
        else {
            logger.info(" - Using cached data source (" + dataSourceId + ")");
        }
        return dataSourcePath;
    }

    private void downloadDataSource(String dataSourceId, File destination, RequestLogger logger) throws IOException {
        FileUtils.createNew(destination);
        try(InputStream inputStream = master.getDataSource(dataSourceId);
            OutputStream outputStream = new FileOutputStream(destination)) {
            IOUtils.copy(inputStream, outputStream);
        }
        catch (Exception cause) {
            logger.error(" - Failed to download data source (" + dataSourceId + ")", cause);
            throw new ConnectionException(cause);
        }
    }
}
