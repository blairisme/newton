/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.network;

import java.util.function.Predicate;

/**
 * Implementors of this interface provide a strategy for handling responses
 * from a REST service.
 *
 * @author Blair Butterworth
 */
public interface RestStatusStrategy extends Predicate<RestResponse> {
}
