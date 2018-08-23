/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.fizzyo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ucl.newton.common.concurrent.DaemonThreadFactory;
import org.ucl.newton.common.serialization.CsvSerializer;
import org.ucl.newton.sdk.plugin.*;
import org.ucl.newton.sdk.provider.BasicDataProvider;
import org.ucl.newton.sdk.provider.BasicDataSource;
import org.ucl.newton.sdk.provider.DataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    private static Logger logger = LoggerFactory.getLogger(FizzyoDataProvider.class);
    private ScheduledExecutorService scheduler;
    private Collection<DataSource> dataSources;
    private GetFizzyoData handler;


    public FizzyoDataProvider(){
        this.dataSources = new ArrayList<>();

        this.scheduler = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());
        this.handler = new GetFizzyoData(this);

        this.dataSources.add(new BasicDataSource(this,"pressure-raw.csv","pressure-raw"));
        this.dataSources.add(new BasicDataSource(this,"exercise-sessions.csv","exercise-sessions"));
        this.dataSources.add(new BasicDataSource(this,"foot-steps.csv","foot-steps"));
        this.dataSources.add(new BasicDataSource(this,"foot-steps-granular.csv","foot-steps-granular"));
        this.dataSources.add(new BasicDataSource(this,"games-sessions.csv","games-sessions"));
        this.dataSources.add(new BasicDataSource(this,"heart-rate.csv","heart-rate"));
    }

    @Override
    public String getIdentifier() {
        return "newton-fizzyo";
    }

    @Override
    public PluginConfiguration getConfiguration() {
        return handler.getConfiguration();
    }

    @Override
    public PluginVisualization getVisualization() {
        return new BasicVisualization("Fizzyo Data Provider", "Obtains data from the Fizzyo cloud.");
    }

    @Override
    public void setContext(PluginHostContext context) {
        InputStream input = null;
        try {
            input = context.getStorage().getInputStream("FizzyoConfiguration");
        }catch (IOException e){
            logger.error("Fail to load Fizzyo configuration and load default configuration instead:", e);
        }
        if (input == null)
            input = getClass().getResourceAsStream("/configuration/FizzyoConfiguration");
        FizzyoConfiguration configuration = readFizzyoConfiguration(input);
        if (configuration != null) {
            configuration.setContext(context);
            handler.setConfiguration(configuration);
        }
    }

    private FizzyoConfiguration readFizzyoConfiguration(InputStream input) {
        FizzyoConfiguration configuration = null;
        List<String[]> configs = CsvSerializer.readCSV(input);
        if(configs.size()>0){
            configuration = new FizzyoConfiguration(configs.get(0));
        }
        return configuration;
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
