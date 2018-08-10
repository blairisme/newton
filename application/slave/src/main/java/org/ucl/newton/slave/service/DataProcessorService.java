/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.ucl.newton.bridge.ExecutionCoordinator;
import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.common.exception.ConnectionException;
import org.ucl.newton.common.exception.InvalidPluginException;
import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.common.lang.JarClassLoader;
import org.ucl.newton.common.lang.JarInstanceLoader;
import org.ucl.newton.common.process.CommandExecutor;
import org.ucl.newton.common.process.CommandExecutorFactory;
import org.ucl.newton.sdk.processor.DataProcessor;
import org.ucl.newton.slave.application.ApplicationStorage;
import org.ucl.newton.slave.engine.RequestContext;
import org.ucl.newton.slave.engine.RequestLogger;
import org.ucl.newton.slave.engine.RequestWorkspace;
import org.ucl.newton.slave.network.MasterServerFactory;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toSet;
import static org.ucl.newton.common.file.PathUtils.toUrl;

/**
 * Instances of this class manage a local cache of {@link DataProcessor
 * DataProcessors}.
 *
 * @author Ziad Halabi
 * @author Blair Butterworth
 */
@Service
public class DataProcessorService
{
    private ApplicationStorage storage;
    private ExecutionCoordinator master;
    private CommandExecutorFactory executorFactory;

    @Inject
    public DataProcessorService(
        ApplicationStorage storage,
        MasterServerFactory serverFactory,
        CommandExecutorFactory executorFactory)
    {
        this.storage = storage;
        this.master = serverFactory.get();
        this.executorFactory = executorFactory;
    }

    public void invokeProcessor(ExecutionRequest request, RequestContext context)
        throws IOException, InvalidPluginException
    {
        RequestLogger logger = context.getLogger();
        RequestWorkspace workspace = context.getWorkspace();

        logger.info("Obtaining data processor");
        DataProcessor processor = getDataProcessor(request, logger);

        logger.info("Executing user script " + request.getScript());
        processor.process(getExecutor(workspace), workspace.getScript());

        logger.info("Execution complete " + request.getScript());
    }

    private DataProcessor getDataProcessor(ExecutionRequest request, RequestLogger logger)
        throws IOException, InvalidPluginException
    {
        String processorId = request.getProcessor();
        Path processorPlugin = getProcessorPlugin(processorId, logger);
        return loadProcessor(processorPlugin, processorId, logger);
    }

    private Path getProcessorPlugin(String processorId, RequestLogger logger)
        throws IOException
    {
        logger.info(" - Experiment depends on data processor: " + processorId);

        Path processorPath = storage.getProcessorDirectory().resolve(processorId + ".jar");
        File processorFile = processorPath.toFile();

        if (!processorFile.exists()) {
            logger.info(" - Downloading latest data processor version (" + processorId + ")");
            downloadProcessorPlugin(processorId, processorFile, logger);
        }
        else {
            logger.info(" - Using cached data processor (" + processorId + ")");
        }
        return processorPath;
    }

    private void downloadProcessorPlugin(String processorId, File destination, RequestLogger logger)
        throws IOException
    {
        FileUtils.createNew(destination);
        try (InputStream inputStream = master.getDataProcessor(processorId);
            OutputStream outputStream = new FileOutputStream(destination)) {
            IOUtils.copy(inputStream, outputStream);
        }
        catch (Exception cause) {
            logger.error(" - Failed to download data processor: " + processorId, cause);
            throw new ConnectionException(cause);
        }
    }

    private DataProcessor loadProcessor(Path processorPlugin, String processorId, RequestLogger logger)
        throws InvalidPluginException
    {
        try {
            JarClassLoader classLoader = new JarClassLoader(toUrl(processorPlugin));
            JarInstanceLoader instanceLoader = new JarInstanceLoader(classLoader);
            Set<DataProcessor> processors = instanceLoader.getImplementors(DataProcessor.class, "org.ucl.newton");
            return findProcessor(processors, processorId);
        }
        catch (Exception cause) {
            logger.error(" - Data processor corrupt: " + processorId, cause);
            throw new InvalidPluginException(cause);
        }
    }

    private DataProcessor findProcessor(Set<DataProcessor> processors, String processorId) {
        Set<DataProcessor> results = processors.stream().filter(byProcessorId(processorId)).collect(toSet());

        if (results.isEmpty()) {
            throw new InvalidPluginException("Processor missing: " + processorId);
        }
        if (results.size() > 1) {
            throw new InvalidPluginException("Multiple processor implementations: " + processorId);
        }
        return results.iterator().next();
    }

    private Predicate<DataProcessor> byProcessorId(String processorId) {
        return (processor) -> Objects.equals(processor.getIdentifier(), processorId);
    }

    private CommandExecutor getExecutor(RequestWorkspace workspace) {
        CommandExecutor executor = executorFactory.get();
        executor.workingDirectory(workspace.getRoot());
        executor.redirectError(workspace.getLog());
        executor.redirectOutput(workspace.getLog());
        return executor;
    }
}
