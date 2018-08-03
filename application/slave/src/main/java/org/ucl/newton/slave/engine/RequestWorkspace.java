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

import javax.inject.Named;
import java.nio.file.Path;

/**
 * Specifies that location of content involved in an execution request.
 *
 * @author Ziad Halabi
 * @author Blair Butterworth
 */
public class RequestWorkspace
{
    private Path root;
    private Path data;
    private Path repo;
    private Path script;
    private Path log;
    private Path output;

    public RequestWorkspace(Path workspaceDirectory, ExecutionRequest request) {
        root = workspaceDirectory.resolve(request.getExperiment()).resolve(request.getVersion());
        data = root.resolve("data");
        repo = root;
        script = root.resolve(request.getScript());
        log = root.resolve("log.txt");
        output = root.resolve("outputs.zip");
    }

    public Path getRoot() {
        return root;
    }

    public Path getData() {
        return data;
    }

    public Path getRepository() {
        return repo;
    }

    public Path getLog() {
        return log;
    }

    public Path getScript() {
        return script;
    }

    public Path getOutput() {
        return output;
    }
}
