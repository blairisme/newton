/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.sdk.data;

import org.ucl.newton.sdk.plugin.NewtonPlugin;

/**
 * Implementors of this interface provide data to the Newton system.
 *
 * @author Blair Butterworth
 * @author Xiaolong Chen
 */
public interface DataProvider extends NewtonPlugin
{
    void start(StorageProvider storageProvider);

    void stop();

    void addObserver(DataProviderObserver observer);

    void removeObserver(DataProviderObserver observer);
}
