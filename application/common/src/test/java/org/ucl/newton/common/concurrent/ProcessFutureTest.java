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
import org.mockito.Mockito;

import java.util.concurrent.TimeUnit;

/**
 * Unit tests for the {@link ProcessFuture} class.
 *
 * @author Blair Butterworth
 */
public class ProcessFutureTest
{
    @Test
    public void cancelAliveTest() {
        Process process = Mockito.mock(Process.class);
        ProcessFuture future = new ProcessFuture(process);

        Mockito.when(process.isAlive()).thenReturn(true);
        boolean result = future.cancel(false);

        Assert.assertTrue(result);
        Mockito.verify(process, Mockito.times(1)).destroy();
    }

    @Test
    public void cancelFinishedTest() {
        Process process = Mockito.mock(Process.class);
        ProcessFuture future = new ProcessFuture(process);

        Mockito.when(process.isAlive()).thenReturn(false);
        boolean result = future.cancel(false);

        Assert.assertFalse(result);
        Mockito.verify(process, Mockito.never()).destroy();
    }

    @Test
    public void isDoneTest() {
        Process process = Mockito.mock(Process.class);
        ProcessFuture future = new ProcessFuture(process);

        Mockito.when(process.isAlive()).thenReturn(true);
        Assert.assertFalse(future.isDone());

        Mockito.when(process.isAlive()).thenReturn(false);
        Assert.assertTrue(future.isDone());
    }

    @Test
    public void getTest() throws Exception {
        Process process = Mockito.mock(Process.class);
        ProcessFuture future = new ProcessFuture(process);

        future.get();
        Mockito.verify(process, Mockito.times(1)).waitFor();

        future.get(1, TimeUnit.SECONDS);
        Mockito.verify(process, Mockito.times(1)).waitFor(1, TimeUnit.SECONDS);
    }
}
