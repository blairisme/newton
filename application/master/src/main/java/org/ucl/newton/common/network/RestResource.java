/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */


package org.ucl.newton.common.network;

/**
 * Implementors of this interface represent the address of an HTTP resource to
 * which a REST call can be made.
 *
 * @author Blair Butterworth
 */
public interface RestResource
{
    /**
     * Returns the path segment of the URL of the REST resource.
     *
     * @return a URL path segment. E.g., fhir/patients.
     */
    String getPath();
}
