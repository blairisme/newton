/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.bridge;

/**
 * Instances of this class represent work that can be executed on an
 * {@link ExecutionNode}.
 *
 * @author Blair Butterworth
 */
public class ExecutionRequest
{
    private String id;
    private String mainFilename;
    private String repoUrl;
    private int type;
    private String outputPattern;

    public ExecutionRequest() {
    }

    public ExecutionRequest(String id, String mainFilename, String repoUrl, int type, String outputPattern) {
        this.id = id;
        this.mainFilename = mainFilename;
        this.repoUrl = repoUrl;
        this.type = type;
        this.outputPattern = outputPattern;
    }

    public String getId() {
        return id;
    }

    public String getMainFilename() {
        return mainFilename;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public int getType() {
        return type;
    }

    public String getOutputPattern() {
        return outputPattern;
    }
}
