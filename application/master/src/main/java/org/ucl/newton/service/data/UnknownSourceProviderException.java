package org.ucl.newton.service.data;

/**
 * Instances of this class are thrown when performing an operation on a source provider
 * that doesn't exist.
 *
 * @author Blair Butterworth
 */
public class UnknownSourceProviderException extends RuntimeException  {
    public UnknownSourceProviderException(int id) {
        super("Missing Source Provider: " + id);
    }
}
