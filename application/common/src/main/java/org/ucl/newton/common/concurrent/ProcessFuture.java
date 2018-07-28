/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.concurrent;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * A {@link Future} implementation wrapping a running {@link Process} instance.
 *
 * @author Blair Butterworth
 */
public class ProcessFuture implements Future<Void>
{
    private Process process;
    private boolean cancelled;

    public ProcessFuture(Process process) {
        this.process = process;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        cancelled = process.isAlive();
        process.destroy();
        return cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public boolean isDone() {
        return !process.isAlive();
    }

    @Override
    public Void get() throws InterruptedException {
        process.waitFor();
        return null;
    }

    @Override
    public Void get(long timeout, TimeUnit unit) throws InterruptedException {
        process.waitFor(timeout, unit);
        return null;
    }
}