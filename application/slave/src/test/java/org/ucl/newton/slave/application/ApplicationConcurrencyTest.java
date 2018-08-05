/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.application;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

public class ApplicationConcurrencyTest
{
    @Test
    public void experimentExecutorTest() {
        ApplicationConcurrency configuration = new ApplicationConcurrency();
        Executor executor = configuration.experimentExecutor();

        Assert.assertNotNull(executor);
        Assert.assertTrue(((ThreadPoolTaskExecutor)executor).isDaemon());
    }
}
