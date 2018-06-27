/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.data;

import org.ucl.newton.service.data.sdk.StorageProvider;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Instances of this class provide a file based StorageProvider implementation.
 *
 * @author Blair Butterworth
 */
public class DataStorage implements StorageProvider
{
    private String providerId;

    public DataStorage(String providerId) {

    }

    @Override
    public InputStream getInputStream(String id) {
        return null;
    }

    @Override
    public OutputStream getOutputStream(String id) {
        return null;
    }
}
