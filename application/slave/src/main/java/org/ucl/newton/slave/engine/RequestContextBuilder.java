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
import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.slave.application.ApplicationStorage;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Builds {@link RequestContext RequestContexts} from given
 * {ExecutionRequest ExecutionRequests}.
 *
 * @author Blair Butterworth
 * @author Ziad Halabi
 */
@Named
public class RequestContextBuilder
{
    private ApplicationStorage storage;

    @Inject
    public RequestContextBuilder(ApplicationStorage storage) {
        this.storage = storage;
    }

    public RequestContext newContext(ExecutionRequest request) {
        RequestWorkspace workspace = new RequestWorkspace(storage.getWorkspaceDirectory(), request);
        RequestLogger logger = new RequestLogger(workspace);
        Stopwatch timer = Stopwatch.createStarted();
        return new RequestContext(workspace, logger, timer);
    }
}
