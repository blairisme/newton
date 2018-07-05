/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.network;

import java.util.Arrays;
import java.util.Collection;

/**
 * Implementors of this class provide common strategies for handling responses
 * from a REST service.
 *
 * @author Blair Butterworth
 */
public class RestStatusStrategies
{
    public static RestStatusStrategy throwOnFailure() {
        return (RestResponse response) -> {
            int code = response.getStatusCode();
            return (code >= 200 && code <= 299);
        };
    }

    public static RestStatusStrategy throwOnFailureExcept(Integer ... excluding) {
        Collection<Integer> excludes = Arrays.asList(excluding);
        return throwOnFailureExcept(excludes);
    }

    public static RestStatusStrategy throwOnFailureExcept(Collection<Integer> excluding) {
        return (RestResponse response) -> {
            int code = response.getStatusCode();
            return (code >= 200 && code <= 299) || excluding.contains(code);
        };
    }
}
