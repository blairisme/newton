/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.data;


import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.sdk.data.DataSource;
import org.ucl.newton.sdk.data.DataStorage;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * Instances of this class provide a file based StorageProvider implementation.
 *
 * @author Blair Butterworth
 */
@Named
public class DataStorageProvider implements DataStorage
{
    private String providerId;
    private ApplicationStorage applicationStorage;

    @Inject
    public DataStorageProvider(ApplicationStorage applicationStorage) {
        this.applicationStorage = applicationStorage;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Override
    public InputStream getInputStream(DataSource dataSource) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public OutputStream getOutputStream(DataSource dataSource) throws IOException {
        return applicationStorage.getOutputStream("data/" + providerId, dataSource.getIdentifier());
    }

    public Path getPath(DataSource dataSource) {
        Path dataDirectory = applicationStorage.getDataDirectory();
        Path providerDirectory = dataDirectory.resolve(providerId);
        return providerDirectory.resolve(dataSource.getIdentifier());
    }
}
