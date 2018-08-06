/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configures the thread pools used by the system.
 *
 * @author Ziad Halabi
 * @author Blair Butterworth
 */
@EnableAsync
@Configuration
@SuppressWarnings("unused")
public class ApplicationConcurrency
{
    @Bean(name = "experiment")
    public Executor experimentExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setDaemon(true);
        executor.setThreadNamePrefix("Experiment-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "network")
    public Executor networkExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(1000);
        executor.setDaemon(true);
        executor.setThreadNamePrefix("Network-");
        executor.initialize();
        return executor;
    }
}
