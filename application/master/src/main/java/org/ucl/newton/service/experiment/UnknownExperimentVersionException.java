package org.ucl.newton.service.experiment;

/**
 * Exception thrown when an experiment is searched for a version it doesn't contain.
 *
 * @author John Wilkie
 */
public class UnknownExperimentVersionException extends RuntimeException {

    public UnknownExperimentVersionException(String experimentName, int versionNum) {
        super(String.format("Experiment %s does not have a version with " +
                "number: %d", experimentName, versionNum));
    }
}
