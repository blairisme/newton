/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.network;

import org.springframework.util.concurrent.ListenableFutureCallback;
import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.bridge.ExecutionResult;

/**
 * Monitors the asynchronous execution of an {@link ExecutionRequest},
 * reporting its success or failure back to the slave server which will
 * communicate the result with the master server.
 *
 * @author Blair Butterworth
 * @author Ziad Halabi
 */
public class ExecutionObserver implements ListenableFutureCallback<ExecutionResult>
{
    private SlaveServer handler;
    private ExecutionRequest request;

    public ExecutionObserver(SlaveServer handler, ExecutionRequest request) {
        this.request = request;
        this.handler = handler;
    }

    @Override
    public void onSuccess(ExecutionResult result) {
        handler.executionSuccess(request, result);
    }

    @Override
    public void onFailure(Throwable error) {
        handler.executionFailure(request, error);
    }
}
