/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.bridge;

import java.io.InputStream;

/**
 * Instances of this class represent a remote system capable of executing an
 * experiment.
 *
 * @author Blair Butterworth
 */
public interface ExecutionNode
{
    void setAddress(String address);

    void execute(ExecutionRequest executionRequest) throws ExecutionException;

    void cancel(ExecutionRequest executionRequest) throws ExecutionException;

    InputStream getExecutionLog(ExecutionResult executionResult) throws ExecutionException;

    InputStream getExecutionOutput(ExecutionResult executionResult) throws ExecutionException;
}
