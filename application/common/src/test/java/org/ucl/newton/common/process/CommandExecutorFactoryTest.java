/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.process;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@link CommandExecutorFactory} class.
 *
 * @author Blair Butterworth
 */
public class CommandExecutorFactoryTest
{
    @Test
    public void getTest() {
        CommandExecutorFactory factory = new CommandExecutorFactory();
        CommandExecutor executor = factory.get();
        Assert.assertNotNull(executor);
    }
}
