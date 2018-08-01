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
 * Instances of this class represent the system coordinating remote execution
 * on {@link ExecutionNode ExecutionNodes}.
 *
 * @author Blair Butterworth
 */
public interface ExecutionCoordinator
{
    InputStream getDataSource(String dataSourceId) throws ExecutionException;

    InputStream getDataProcessor(String dataProcessorId) throws ExecutionException;

    InputStream getExperimentRepository(String experimentId) throws ExecutionException;

    void executionComplete(ExecutionResult result);

    void executionFailed(ExecutionFailure failure);

    void setHost(String host);

    void setPort(int port);
}
