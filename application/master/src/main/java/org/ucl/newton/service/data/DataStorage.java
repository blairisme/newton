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
import org.ucl.newton.sdk.data.StorageProvider;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Instances of this class provide a file based StorageProvider implementation.
 *
 * @author Blair Butterworth
 */
@Named
public class DataStorage implements StorageProvider
{
    private String providerId;
    private ApplicationStorage applicationStorage;

    @Inject
    public DataStorage(ApplicationStorage applicationStorage) {
        this.applicationStorage = applicationStorage;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Override
    public InputStream getInputStream(String id) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public OutputStream getOutputStream(String id) throws IOException {
        return applicationStorage.getOutputStream("data/" + providerId, id);
    }
}
