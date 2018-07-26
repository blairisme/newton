package org.ucl.FizzyoDataProvider.Fizzyo;

import org.ucl.newton.service.data.sdk.DataProvider;
import org.ucl.newton.service.data.sdk.DataProviderObserver;
import org.ucl.newton.service.data.sdk.StorageProvider;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * Instances of this class provide utils for file reading.
 *
 * @author Xiaolong Chen
 */
public class FizzyoDataProvider implements DataProvider {
    private DataProviderObserver observer;
    private ScheduledExecutorService scheduler;

    @Override
    public void start(StorageProvider storageProvider) {
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.scheduler.scheduleAtFixedRate(new GetFizzyoData(storageProvider),0,1,TimeUnit.HOURS); //run every hour
        observer.dataUpdated();
    }

    @Override
    public void stop() {
        this.scheduler.shutdown();
    }

    @Override
    public void addObserver(DataProviderObserver observer) {
        this.observer = observer;
    }

    @Override
    public void removeObserver(DataProviderObserver observer) {
        this.observer = null;
    }
}
