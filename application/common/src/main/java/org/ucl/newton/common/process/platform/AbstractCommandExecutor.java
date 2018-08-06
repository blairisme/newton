/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.process.platform;

import org.ucl.newton.common.concurrent.ProcessFuture;
import org.ucl.newton.common.process.CommandExecutionException;
import org.ucl.newton.common.process.CommandExecutor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * A base class for {@link CommandExecutor} implemenations, catering for common
 * use.
 *
 * @author Blair Butterworth
 */
public abstract class AbstractCommandExecutor implements CommandExecutor
{
    private Path errorRedirect;
    private Path outputRedirect;
    private Path workingDirectory;

    public AbstractCommandExecutor() {
    }

    @Override
    public void execute(List<String> commands) throws CommandExecutionException {
        try {
            Future<Void> future = executeAsync(commands);
            future.get();
        }
        catch (InterruptedException | ExecutionException error) {
            throw new CommandExecutionException(error);
        }
    }

    public Future<Void> executeAsync(List<String> commands) throws CommandExecutionException {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(getShellCommands(commands));

            if (workingDirectory != null) {
                processBuilder.directory(workingDirectory.toFile());
            }
            if (errorRedirect != null) {
                processBuilder.redirectError(ProcessBuilder.Redirect.appendTo(errorRedirect.toFile()));
            }
            if (outputRedirect != null) {
                processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(outputRedirect.toFile()));
            }
            return new ProcessFuture(processBuilder.start());
        }
        catch (IOException error) {
            throw new CommandExecutionException(error);
        }
    }

    public void redirectError(Path path) {
        this.errorRedirect = path;
    }

    public void redirectOutput(Path path) {
        this.outputRedirect = path;
    }

    public void workingDirectory(Path workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    protected abstract List<String> getShellCommands(List<String> commands);
}
