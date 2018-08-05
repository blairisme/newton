/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.engine;

import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.bridge.ExecutionResult;
import org.ucl.newton.slave.service.DataProcessorService;
import org.ucl.newton.slave.service.DataSourceService;
import org.ucl.newton.slave.service.ExperimentService;
import org.ucl.newton.slave.service.WorkspaceService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

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

    public ExecutionResult process(ExecutionRequest request) throws IOException {
        RequestContext context = requestContextBuilder.newContext(request);
        experimentService.addRepository(request, context);
        dataSourceService.addDataSources(request, context);
        dataProcessorService.invokeProcessor(request, context);
        return workspaceService.collateResults(request, context);
    }
}
