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
 * Executes a given command line in a shell appropriate to the host operating
 * system.
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

    /**
     * Asynchronously executes the given commands in a new command process.
     *
     * @param commands  a {@link List} of commands that will be executed.
     * @return          a {@link Future} instance that can be used to monitor
     *                  the asynchronous process.
     *
     * @throws CommandExecutionException    thrown if an error occurs executing
     *                                      the given commands.
     */
    Future<Void> executeAsync(List<String> commands) throws CommandExecutionException;

    /**
     * Redirects any error output into the file at given {@link Path}.
     *
     * @param path the path to a file that will receive error output.
     */
    void redirectError(Path path);

    /**
     * Redirects any output into the file at given {@link Path}.
     *
     * @param path the path to a file that will receive output.
     */
    void redirectOutput(Path path);

    /**
     * Sets the initial directory of the executing command.
     *
     * @param workingDirectory a file path.
     */
    void workingDirectory(Path workingDirectory);
}
