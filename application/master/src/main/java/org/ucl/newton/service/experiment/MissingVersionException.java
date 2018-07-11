/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.experiment;

/**
 * Exception thrown when an experiment is searched for a version it doesn't contain.
 *
 * @author John Wilkie
 */
public class MissingVersionException extends RuntimeException {

    public MissingVersionException(String experimentName, int versionNum) {
        super(String.format("Experiment %s does not have a version with " +
                "number: %d", experimentName, versionNum));
    }
}
