/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.engine;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.bridge.ExecutionResult;
import org.ucl.newton.slave.service.DataProcessorService;
import org.ucl.newton.slave.service.DataSourceService;
import org.ucl.newton.slave.service.ExperimentService;
import org.ucl.newton.slave.service.WorkspaceService;

import javax.inject.Inject;
import javax.inject.Named;

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
    private DataProcessorService dataProcessorService;
    private DataSourceService dataSourceService;
    private ExperimentService experimentService;
    private WorkspaceService workspaceService;
    private RequestContextBuilder requestContextBuilder;

    @Inject
    public RequestHandler(
        DataProcessorService dataProcessorService,
        DataSourceService dataSourceService,
        ExperimentService experimentService,
        WorkspaceService workspaceService,
        RequestContextBuilder requestContextBuilder)
    {
        this.dataProcessorService = dataProcessorService;
        this.dataSourceService = dataSourceService;
        this.experimentService = experimentService;
        this.workspaceService = workspaceService;
        this.requestContextBuilder = requestContextBuilder;
    }

    @Async("experiment")
    public ListenableFuture<ExecutionResult> process(ExecutionRequest request) {
        try {
            RequestContext context = requestContextBuilder.newContext(request);
            experimentService.addRepository(request, context);
            dataSourceService.addDataSources(request, context);
            dataProcessorService.invokeProcessor(request, context);
            ExecutionResult result = workspaceService.collateResults(request, context);
            return new AsyncResult<>(result);
        }
        catch (Throwable error) {
            return AsyncResult.forExecutionException(error);
        }
    }
}
