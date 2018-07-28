/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.sdk.datasource;

/**
 * Implementors of this interface provide data to the Newton system.
 *
 * @author Blair Butterworth
 */
public interface DataProvider
{
    void start(StorageProvider storageProvider);

    void stop();

    void addObserver(DataProviderObserver observer);

    void removeObserver(DataProviderObserver observer);
}
