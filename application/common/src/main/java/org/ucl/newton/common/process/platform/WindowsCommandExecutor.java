/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.process.platform;

import org.ucl.newton.common.process.CommandExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link CommandExecutor} implementation for use on Windows.
 *
 * @author Blair Butterworth.
 */
public class WindowsCommandExecutor extends AbstractCommandExecutor
{
    @Override
    protected List<String> getShellCommands(List<String> commands) {
        List<String> result = new ArrayList<>();
        result.add("cmd");
        result.add("/c");
        result.addAll(commands);
        return result;
    }
}
