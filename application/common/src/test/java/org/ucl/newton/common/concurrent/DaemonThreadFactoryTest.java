/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.concurrent;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.newton.common.collection.CollectionUtils;

/**
 * Unit tests for the {@link CollectionUtils} class.
 *
 * @author Blair Butterworth
 */
public class DaemonThreadFactoryTest
{
    @Test
    public void newThreadTest() {
        DaemonThreadFactory factory = new DaemonThreadFactory();
        Thread thread = factory.newThread(new DummyRunnable());

        Assert.assertNotNull(thread);
        Assert.assertEquals(true, thread.isDaemon());
    }

    private static class DummyRunnable implements Runnable {
        @Override
        public void run() {
        }
    }
}
