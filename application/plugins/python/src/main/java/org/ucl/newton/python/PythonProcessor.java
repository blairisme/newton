/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.python;

import org.ucl.newton.common.process.CommandExecutor;
import org.ucl.newton.sdk.plugin.*;
import org.ucl.newton.sdk.processor.DataProcessor;
import org.ucl.newton.sdk.processor.DataProcessorException;

import java.nio.file.Path;

import static java.util.Arrays.asList;

/**
 * A {@link DataProcessor} implementation that executes a given Python script.
 *
 * @author Blair Butterworth
 */
public class PythonProcessor implements DataProcessor
{
    private static final String PYTHON_COMMAND = "python";

    @Override
    public String getIdentifier() {
        return "newton-python";
    }

    @Override
    public PluginConfiguration getConfiguration() {
        return new BasicConfiguration("python.html");
    }

    @Override
    public PluginVisualization getVisualization() {
        return new BasicVisualization(
            "Newton Python Plugin",
            "Provides the ability to use Python scripts to perform data analysis.");
    }

    @Override
    public void setContext(PluginHostContext context) {
    }

    @Override
    public void process(CommandExecutor commandExecutor, Path scriptPath) throws DataProcessorException {
        String script = "\"" + scriptPath.toString() + "\"";
        commandExecutor.execute(asList(PYTHON_COMMAND, script));
    }
}
