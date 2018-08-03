/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.engine;

import com.google.common.base.Stopwatch;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.utils.URIBuilder;
import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.bridge.ExecutionResult;
import org.ucl.newton.bridge.ExecutionResultBuilder;
import org.ucl.newton.common.archive.ZipUtils;
import org.ucl.newton.common.exception.InvalidPluginException;
import org.ucl.newton.common.file.PathUtils;
import org.ucl.newton.common.lang.Strings;
import org.ucl.newton.common.network.UriSchemes;
import org.ucl.newton.common.network.UrlUtils;
import org.ucl.newton.common.process.CommandExecutor;
import org.ucl.newton.common.process.CommandExecutorFactory;
import org.ucl.newton.sdk.processor.DataProcessor;
import org.ucl.newton.slave.application.ApplicationPreferences;
import org.ucl.newton.slave.service.DataProcessorService;
import org.ucl.newton.slave.service.DataSourceService;
import org.ucl.newton.slave.service.ExperimentService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Processes execution requests, downloading assets required for execution,
 * performing the analysis and collating the results.
 *
 * @author Ziad Halabi
 * @author Blair Butterworth
 */
@Named
public class RequestHandler
{
    private ApplicationPreferences applicationPreferences;
    private CommandExecutorFactory commandExecutorFactory;
    private DataProcessorService dataProcessorService;
    private DataSourceService dataSourceService;
    private ExperimentService experimentService;

    @Inject
    public RequestHandler(
        ApplicationPreferences applicationPreferences,
        CommandExecutorFactory commandExecutorFactory,
        DataProcessorService dataProcessorService,
        DataSourceService dataSourceService,
        ExperimentService experimentService)
    {
        this.applicationPreferences = applicationPreferences;
        this.commandExecutorFactory = commandExecutorFactory;
        this.dataProcessorService = dataProcessorService;
        this.dataSourceService = dataSourceService;
        this.experimentService = experimentService;
    }

    public ExecutionResult process(ExecutionRequest request) throws IOException {
        Stopwatch timer = Stopwatch.createStarted();
        RequestWorkspace workspace = createWorkspace(request);
        addDataSources(request, workspace);
        addRepository(request, workspace);
        executeScript(request, workspace);
        return collateResults(request, workspace, timer);
    }

    private RequestWorkspace createWorkspace(ExecutionRequest request) {
        Path applicationPath = applicationPreferences.getApplicationPath();
        return new RequestWorkspace(applicationPath, request);
    }

    private void addDataSources(ExecutionRequest request, RequestWorkspace workspace) throws IOException {
        for (Path dataSource: dataSourceService.getDataSources(request.getDatasources())) {
            FileUtils.copyFileToDirectory(dataSource.toFile(), workspace.getData().toFile());
        }
    }

    private void addRepository(ExecutionRequest request, RequestWorkspace workspace) throws IOException {
        Path repositoryArchive = experimentService.getRepository(request.getExperiment());
        ZipUtils.unzip(repositoryArchive, workspace.getRepository());
    }

    private void executeScript(ExecutionRequest request, RequestWorkspace workspace) throws IOException, InvalidPluginException {
        CommandExecutor executor = createExecutor(workspace);
        DataProcessor processor = dataProcessorService.getDataProcessor(request.getProcessor());
        processor.process(executor, workspace.getScript());
    }

    private CommandExecutor createExecutor(RequestWorkspace workspace) {
        CommandExecutor executor = commandExecutorFactory.get();
        executor.workingDirectory(workspace.getRoot());
        executor.redirectError(workspace.getLog());
        executor.redirectOutput(workspace.getLog());
        return executor;
    }

    private ExecutionResult collateResults(ExecutionRequest request, RequestWorkspace workspace, Stopwatch timer) throws IOException {
        ExecutionResultBuilder builder = new ExecutionResultBuilder();
        builder.forRequest(request);
        builder.setOutputs(getOutput(request, workspace));
        builder.setDuration(getDuration(timer));
        return builder.build();
    }

    private Duration getDuration(Stopwatch timer) {
        return Duration.ofSeconds(timer.elapsed(TimeUnit.SECONDS));
    }

    private URL getOutput(ExecutionRequest request, RequestWorkspace workspace) throws IOException {
        List<String> patterns = Strings.split(request.getOutput(), ",");
        patterns = Strings.trim(patterns);

        Collection<Path> contents = PathUtils.findChildren(workspace.getRoot(), patterns);
        contents.add(workspace.getLog());

        Path archive = workspace.getOutput();
        ZipUtils.zip(contents, archive);

        Path applicationPath = applicationPreferences.getApplicationPath();
        return UrlUtils.createUrl(getBaseAddress(), applicationPath.relativize(archive));
    }

    private URL getBaseAddress() throws MalformedURLException {
        try {
            URIBuilder builder = new URIBuilder();
            builder.setScheme(UriSchemes.HTTP);
            builder.setHost(applicationPreferences.getApplicationHost());
            builder.setPort(applicationPreferences.getApplicationPort());
            builder.setPath("files");
            return builder.build().toURL();
        }
        catch (URISyntaxException syntaxException) {
            throw new MalformedURLException(syntaxException.getMessage());
        }
    }
}
