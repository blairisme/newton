package org.ucl.newton.service.authentication;

/**
 * Exception used for unknown credential roles
 *
 * @author John Wilkie
 */
public class UnknownRoleException extends Throwable {
    public UnknownRoleException(String role) {
        super(role);
    }
}
