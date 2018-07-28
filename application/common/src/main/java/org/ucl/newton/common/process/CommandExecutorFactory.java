/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.process;

import org.apache.commons.lang3.SystemUtils;
import org.ucl.newton.common.process.platform.LinuxCommandExecutor;
import org.ucl.newton.common.process.platform.WindowsCommandExecutor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

/**
 * Creates new {@link CommandExecutor} instances based on the current platform.
 *
 * @author Blair Butterworth
 */
@Named
public class CommandExecutorFactory implements Provider<CommandExecutor>
{
    @Inject
    public CommandExecutorFactory(){
    }

    @Override
    public CommandExecutor get() {
        if (SystemUtils.IS_OS_WINDOWS) {
            return new WindowsCommandExecutor();
        }
        if (SystemUtils.IS_OS_MAC_OSX || SystemUtils.IS_OS_LINUX) {
            return new LinuxCommandExecutor();
        }
        throw new UnsupportedOperationException();
    }
}
