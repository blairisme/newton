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

import java.util.Arrays;
import java.util.List;

/**
 * A {@link CommandExecutor} implementation for use on Linux.
 *
 * @author Blair Butterworth.
 */
public class UnixCommandExecutor extends AbstractCommandExecutor
{
    @Override
    protected List<String> getShellCommands(List<String> commands) {
        String command = combine(commands);
        return Arrays.asList("bash", "-c", command);
    }

    private String combine(List<String> commands) {
        StringBuilder result = new StringBuilder();
        for (String command: commands) {
            result.append(command);
            result.append(" ");
        }
        return result.toString().trim();
    }
}
