/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.sdk.processor;

import org.ucl.newton.common.process.CommandExecutor;
import org.ucl.newton.sdk.plugin.NewtonPlugin;

import java.nio.file.Path;

/**
 * Represents an application that can perform computation on a given source
 * file.
 *
 * @author Blair Butterworth
 * @author Ziad Al Halabi
 */
public interface DataProcessor extends NewtonPlugin
{
    void process(CommandExecutor commandExecutor, Path script) throws DataProcessorException;
}
