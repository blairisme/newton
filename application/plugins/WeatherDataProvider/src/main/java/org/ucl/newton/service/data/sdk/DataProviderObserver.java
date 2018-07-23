/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.data.sdk;

/**
 * Implementors of this interface provide a method that is called when a data
 * source has been updated.
 *
 * @author Blair Butterworth
 */
public interface DataProviderObserver
{
    void dataUpdated();
}