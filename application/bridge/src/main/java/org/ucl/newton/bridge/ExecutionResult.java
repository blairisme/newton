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
 * Instances of this class contain information about the outcome of an
 * execution request.
 *
 * @author Blair Butterworth
 * @author Ziad Al Halabi
 */
public class ExecutionResult
{
    private String id;
    private String zipOutputFileUrl;
    private String logFileUrl;
    private String errorMessage;

    public ExecutionResult() {
    }

    public ExecutionResult(String id, String zipOutputFileUrl, String logFileUrl, String errorMessage) {
        this.id = id;
        this.zipOutputFileUrl = zipOutputFileUrl;
        this.logFileUrl = logFileUrl;
        this.errorMessage = errorMessage;
    }

    public String getId() {
        return id;
    }

    public String getZipOutputFileUrl() {
        return zipOutputFileUrl;
    }

    public String getLogFileUrl() {
        return logFileUrl;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
