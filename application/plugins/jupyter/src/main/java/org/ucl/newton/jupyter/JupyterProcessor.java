/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.jupyter;

import org.ucl.newton.common.collection.CollectionUtils;
import org.ucl.newton.common.process.CommandExecutor;
import org.ucl.newton.sdk.plugin.BasicConfiguration;
import org.ucl.newton.sdk.plugin.BasicVisualization;
import org.ucl.newton.sdk.plugin.PluginConfiguration;
import org.ucl.newton.sdk.plugin.PluginVisualization;
import org.ucl.newton.sdk.processor.DataProcessor;
import org.ucl.newton.sdk.processor.DataProcessorException;

import java.nio.file.Path;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

/**
 * A {@link DataProcessor} implementation that executes a given Jupyter
 * notebook and converts it into HTML.
 *
 * @author Blair Butterworth
 */
@SuppressWarnings("unused")
public class JupyterProcessor implements DataProcessor
{
    private static final List<String> GENERATE_HTML = unmodifiableList(asList("jupyter", "nbconvert", "--to", "html"));
    private static final List<String> GENERATE_SCRIPT = unmodifiableList(asList("jupyter", "nbconvert", "--to", "script"));
    private static final List<String> INVOKE_SCRIPT = unmodifiableList(asList("ipython"));

    @Override
    public String getIdentifier() {
        return "newton-jupyter";
    }

    @Override
    public PluginConfiguration getConfiguration() {
        return new BasicConfiguration("jupyter.html");
    }

    @Override
    public PluginVisualization getVisualization() {
        return new BasicVisualization(
            "Newton Jupyter Plugin",
            "Provides the ability to use Jupyter notebooks to perform data analysis.");
    }

    @Override
    public void process(CommandExecutor commandExecutor, Path script) throws DataProcessorException {
        String notebookPath = "\"" + script.toString() + "\"";
        String scriptPath = notebookPath.replace(".ipynb", ".py");

        commandExecutor.execute(CollectionUtils.append(GENERATE_HTML, notebookPath));
        commandExecutor.execute(CollectionUtils.append(GENERATE_SCRIPT, notebookPath));
        commandExecutor.execute(CollectionUtils.append(INVOKE_SCRIPT, scriptPath));
    }
}
