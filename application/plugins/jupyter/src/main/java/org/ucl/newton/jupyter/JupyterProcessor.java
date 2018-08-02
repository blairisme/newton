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
import org.ucl.newton.sdk.processor.DataProcessor;
import org.ucl.newton.sdk.processor.DataProcessorException;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
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

    @Override
    public String getIdentifier() {
        return "newton-jupyter";
    }

    @Override
    public String getName() {
        return "Newton Jupyter Plugin";
    }

    @Override
    public String getDescription() {
        return "Provides the ability to use Jupyter notebooks to perform data analysis.";
    }

    @Override
    public void process(CommandExecutor commandExecutor, Path script) throws DataProcessorException {
        String scriptName = script.getFileName().toString();

        commandExecutor.workingDirectory(script.getParent());
        commandExecutor.execute(CollectionUtils.append(GENERATE_HTML, scriptName));
        commandExecutor.execute(CollectionUtils.append(GENERATE_SCRIPT, scriptName));
        //execute script
    }
}
