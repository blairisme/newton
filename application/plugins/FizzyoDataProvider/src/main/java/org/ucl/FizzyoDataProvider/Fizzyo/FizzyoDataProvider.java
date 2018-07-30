package org.ucl.FizzyoDataProvider.Fizzyo;

import org.ucl.newton.sdk.data.DataProvider;
import org.ucl.newton.sdk.data.DataProviderObserver;
import org.ucl.newton.sdk.data.StorageProvider;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * Instances of this class provide utils for file reading.
 *
 * @author Xiaolong Chen
 */
public class FizzyoDataProvider implements DataProvider
{
    private DataProviderObserver observer;
    private ScheduledExecutorService scheduler;

    @Override
    public String getIdentifier() {
        return "newton-fizzyo";
    }

    @Override
    public String getName() {
        return "Fizzyo Data Provider";
    }

    @Override
    public String getDescription() {
        return "Obtains data from the Fizzyo cloud.";
    }

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
