/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.execution;

import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.framework.Experiment;

import java.util.UUID;

/**
 * Instances of this class build {@link ExecutionRequest} objects.
 *
 * @author Blair Butterworth
 */
public class ExecutionRequestBuilder
{
    private String id;
    private int experimentId;
    private String mainFilename;
    private String repoUrl;
    private int type;
    private String outputPattern;

    public ExecutionRequestBuilder() {
        this.id = UUID.randomUUID().toString();
        this.type = 0;
        this.mainFilename = "test.py";
        this.repoUrl = "https://github.com/ziad-alhalabi/python-test/archive/master.zip";
        this.outputPattern = "*.py";
    }

    public ExecutionRequestBuilder forExperiment(Experiment experiment) {
        experimentId = experiment.getId();
        return this;
    }

    public ExecutionRequest build() {
        return new ExecutionRequest(id, experimentId, mainFilename, repoUrl, type, outputPattern);
    }
}
