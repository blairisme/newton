/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.sdk.provider;

import java.util.ArrayList;
import java.util.List;

/**
 * A general purpose {@link DataProvider} implementation.
 *
 * @author Blair Butterworth
 */
public abstract class BasicDataProvider implements DataProvider
{
    protected DataStorage storage;
    protected List<DataProviderObserver> observers;

    public BasicDataProvider() {
        observers = new ArrayList<>();
    }

    @Override
    public void addObserver(DataProviderObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(DataProviderObserver observer) {
        observers.remove(observer);
    }

    public void notifyDataUpdated(DataSource dataSource) {
        for (DataProviderObserver observer: observers){
            observer.dataUpdated(dataSource);
        }
    }

    @Override
    public DataStorage getStorage() {
        return storage;
    }

    @Override
    public void setStorage(DataStorage storage) {
        this.storage = storage;
    }
}
