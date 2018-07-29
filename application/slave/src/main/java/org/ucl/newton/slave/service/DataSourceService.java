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
import javax.inject.Named;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

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

    public Collection<Path> getDataSources(Collection<String> dataSourceIds) throws IOException {
        Collection<Path> result = new ArrayList<>();
        for (String dataSourceId: dataSourceIds) {
            result.add(getDataSource(dataSourceId));
        }
        return result;
    }

    private Path getDataSource(String dataSourceId) throws IOException {
        Path dataSourcePath = storage.getDataDirectory().resolve(dataSourceId);
        File dataSourceFile = dataSourcePath.toFile();

        if (!dataSourceFile.exists()) {
            downloadDataSource(dataSourceId, dataSourceFile);
        }
        return dataSourcePath;
    }

    private void downloadDataSource(String dataSourceId, File destination) throws IOException {
        FileUtils.createNew(destination);
        try(InputStream inputStream = master.getDataSource(dataSourceId);
            OutputStream outputStream = new FileOutputStream(destination)) {
            IOUtils.copy(inputStream, outputStream);
        }
        catch (Exception cause) {
            throw new ConnectionException(cause);
        }
    }
}
