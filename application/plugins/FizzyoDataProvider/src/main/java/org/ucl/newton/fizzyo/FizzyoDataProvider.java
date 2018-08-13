/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.fizzyo;

import org.ucl.newton.common.concurrent.DaemonThreadFactory;
import org.ucl.newton.sdk.plugin.*;
import org.ucl.newton.sdk.provider.BasicDataProvider;
import org.ucl.newton.sdk.provider.BasicDataSource;
import org.ucl.newton.sdk.provider.DataSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Instances of this class provide data from Fizzyo.
 *
 * @author Xiaolong Chen
 */
public class FizzyoDataProvider extends BasicDataProvider
{
    private ScheduledExecutorService scheduler;
    private Collection<DataSource> dataSources;
    private GetFizzyoData handler;

    public FizzyoDataProvider(){
        this.dataSources = new ArrayList<>();
        this.dataSources.add(new BasicDataSource(this, "fizzyo", "Fizzyo Data"));
        this.scheduler = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());
        this.handler = new GetFizzyoData(this);
    }

    @Override
    public String getIdentifier() {
        return "newton-fizzyo";
    }

    @Override
    public PluginConfiguration getConfiguration() {
        return new BasicConfiguration("fizzyo.html");
    }

    @Override
    public PluginVisualization getVisualization() {
        return new BasicVisualization("Fizzyo Data Provider", "Obtains data from the Fizzyo cloud.");
    }

    @Override
    public void setContext(PluginHostContext context) {
    }

    public DataSource getFizzyoDataSource() {
        return dataSources.iterator().next();
    }

    @Override
    public Collection<DataSource> getDataSources() {
        return dataSources;
    }

    @Override
    public void start() {
        this.scheduler.scheduleAtFixedRate(handler, 0, 1, TimeUnit.HOURS); //run every hour
    }

    @Override
    public void stop() {
        this.scheduler.shutdown();
    }
}
