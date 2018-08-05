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

/**
 * Contains information specific to the execution of an experiment.
 *
 * @author Blair Butterworth
 * @author Ziad Halabi
 */
public class RequestContext
{
    private RequestWorkspace workspace;
    private RequestLogger logger;
    private Stopwatch timer;

    public RequestContext(RequestWorkspace workspace, RequestLogger logger, Stopwatch timer) {
        this.workspace = workspace;
        this.logger = logger;
        this.timer = timer;
    }

    public RequestWorkspace getWorkspace() {
        return workspace;
    }

    public RequestLogger getLogger() {
        return logger;
    }

    public Stopwatch getTimer() {
        return timer;
    }
}
