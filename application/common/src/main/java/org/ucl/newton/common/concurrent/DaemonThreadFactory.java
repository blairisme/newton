/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * A {@link ThreadFactory} implementation that creates daemon threads using
 * the {@link Executors#defaultThreadFactory() defaultThreadFactory}.
 *
 * @author Blair Butterworth
 */
public class DaemonThreadFactory implements ThreadFactory
{
    private ThreadFactory defaultFactory;

    public DaemonThreadFactory() {
        defaultFactory = Executors.defaultThreadFactory();
    }

    public Thread newThread(Runnable runnable) {
        Thread thread = defaultFactory.newThread(runnable);
        thread.setDaemon(true);
        return thread;
    }
}
