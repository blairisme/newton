/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.service;

import com.google.common.base.Stopwatch;
import org.springframework.stereotype.Service;
import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.bridge.ExecutionResult;
import org.ucl.newton.bridge.ExecutionResultBuilder;
import org.ucl.newton.common.archive.ZipUtils;
import org.ucl.newton.common.file.PathUtils;
import org.ucl.newton.common.lang.Strings;
import org.ucl.newton.common.network.UrlUtils;
import org.ucl.newton.slave.application.ApplicationPreferences;
import org.ucl.newton.slave.application.ApplicationUrls;
import org.ucl.newton.slave.engine.RequestContext;
import org.ucl.newton.slave.engine.RequestLogger;
import org.ucl.newton.slave.engine.RequestWorkspace;

import javax.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Instances of this class are used to obtain the results of experiment
 * execution from the experiment workspace.
 *
 * @author Blair Butterworth
 * @author Ziad Halabi
 */
@Service
public class WorkspaceService
{
    private ApplicationPreferences preferences;
    private ApplicationUrls addresses;

    @Inject
    public WorkspaceService(ApplicationPreferences preferences, ApplicationUrls addresses) {
        this.preferences = preferences;
        this.addresses = addresses;
    }

    public ExecutionResult collateResults(ExecutionRequest request, RequestContext context) throws IOException {
        URL output = getOutput(request, context);
        Duration duration = getDuration(context);
        return getResult(request, output, duration);
    }

    private URL getOutput(ExecutionRequest request, RequestContext context) throws IOException {
        RequestWorkspace workspace = context.getWorkspace();

        RequestLogger logger = context.getLogger();
        logger.info("Collating execution results");

        List<String> patterns = getSearchPattern(request);
        logger.info(" - Search pattern(s): " + patterns);

        Collection<Path> outputs = getOutputs(workspace, patterns);
        logger.info(" - Matching outputs: " + PathUtils.getNames(outputs));

        Path archive = compressOutputs(workspace, outputs);
        logger.info(" - Compressed outputs");

        return getExternalUrl(archive);
    }

    private List<String> getSearchPattern(ExecutionRequest request) {
        String outputPattern = request.getOutput();
        List<String> patterns = Strings.split(outputPattern, ",");
        return Strings.trim(patterns);
    }

    private Collection<Path> getOutputs(RequestWorkspace workspace, List<String> patterns) {
        Collection<Path> contents = PathUtils.findChildren(workspace.getRoot(), patterns);
        contents.add(workspace.getLog());
        return contents;
    }

    private Path compressOutputs(RequestWorkspace workspace, Collection<Path> outputs) throws IOException {
        Path archive = workspace.getOutput();
        ZipUtils.zip(outputs, archive);
        return archive;
    }

    private URL getExternalUrl(Path internalFile) throws MalformedURLException {
        URL filesUrl = addresses.getFilesUrl();
        Path applicationPath = preferences.getApplicationPath();
        return UrlUtils.createUrl(filesUrl, applicationPath.relativize(internalFile));
    }

    private Duration getDuration(RequestContext context) {
        Stopwatch timer = context.getTimer();
        timer = timer.stop();
        return Duration.ofSeconds(timer.elapsed(TimeUnit.SECONDS));
    }

    private ExecutionResult getResult(ExecutionRequest request, URL output, Duration duration) {
        ExecutionResultBuilder builder = new ExecutionResultBuilder();
        builder.forRequest(request);
        builder.setOutputs(output);
        builder.setDuration(duration);
        return builder.build();
    }
}
