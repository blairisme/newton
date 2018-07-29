/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.process;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Future;

/**
 * todo
 *
 * @author Blair Butterworth
 */
public interface CommandExecutor
{
    /**
     * Executes the given commands in a new command process and waits until
     * execution is complete.
     *
     * @param commands  a {@link List} of commands that will be executed.
     *
     * @throws CommandExecutionException    thrown if an error occurs executing
     *                                      the given commands.
     */
    void execute(List<String> commands) throws CommandExecutionException;

    Future<Void> executeAsync(List<String> commands) throws CommandExecutionException;

    void redirectError(Path path);

    void redirectOutput(Path path);

    void workingDirectory(Path workingDirectory);
}
