/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.project;

/**
 * Instances of this class are thrown when performing an operation on a project
 * that doesn't exist.
 *
 * @author Blair Butterworth
 */
public class UnknownProjectException extends RuntimeException
{
    public UnknownProjectException(int id) {
        super("Missing project: " + id);
    }

    public UnknownProjectException(String link) {
        super("Missing project: " + link);
    }
}
