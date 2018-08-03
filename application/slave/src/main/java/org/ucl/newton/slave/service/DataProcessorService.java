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
import org.ucl.newton.common.exception.ConnectionException;
import org.ucl.newton.common.exception.InvalidPluginException;
import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.common.lang.JarClassLoader;
import org.ucl.newton.common.lang.JarInstanceLoader;
import org.ucl.newton.sdk.processor.DataProcessor;
import org.ucl.newton.slave.application.ApplicationStorage;
import org.ucl.newton.slave.network.MasterServerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

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

    @Inject
    public DataProcessorService(ApplicationStorage storage, MasterServerFactory serverFactory) {
        this.storage = storage;
        this.master = serverFactory.get();
    }

    public DataProcessor getDataProcessor(String processorId) throws IOException, InvalidPluginException {
        Path processorPlugin = getProcessorPlugin(processorId);
        return loadProcessor(processorPlugin, processorId);
    }

    private Path getProcessorPlugin(String processorId) throws IOException {
        Path processorPath = storage.getProcessorDirectory().resolve(processorId + ".jar");
        File processorFile = processorPath.toFile();

        if (!processorFile.exists()) {
            downloadProcessorPlugin(processorId, processorFile);
        }
        return processorPath;
    }

    private void downloadProcessorPlugin(String processorId, File destination) throws IOException {
        FileUtils.createNew(destination);
        try (InputStream inputStream = master.getDataProcessor(processorId);
            OutputStream outputStream = new FileOutputStream(destination)) {
            IOUtils.copy(inputStream, outputStream);
        }
        catch (Exception cause) {
            throw new ConnectionException(cause);
        }
    }

    private DataProcessor loadProcessor(Path processorPlugin, String processorId) throws InvalidPluginException {
        try {
            JarClassLoader classLoader = new JarClassLoader(processorPlugin);
            JarInstanceLoader instanceLoader = new JarInstanceLoader(classLoader);
            Set<DataProcessor> processors = instanceLoader.getImplementors(DataProcessor.class, "org.ucl.newton");
            return findProcessor(processors, processorId);
        }
        catch (Exception cause) {
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
}
