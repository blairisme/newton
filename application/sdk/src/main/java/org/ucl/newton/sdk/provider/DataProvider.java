/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.sdk.provider;

import org.ucl.newton.sdk.plugin.NewtonPlugin;

import java.util.Collection;

/**
 * Implementors of this interface provide data to the Newton system.
 *
 * @author Blair Butterworth
 * @author Xiaolong Chen
 */
public interface DataProvider extends NewtonPlugin
{
    void start();

    void stop();

    Collection<DataSource> getDataSources();

    DataStorage getStorage();

    void setStorage(DataStorage storage);

    void addObserver(DataProviderObserver observer);

    void removeObserver(DataProviderObserver observer);
}
