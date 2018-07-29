/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.sdk.plugin;

/**
 * Implementors of this interface extend the behaviour of the Newton system.
 *
 * @author Blair Butterworth
 */
public interface NewtonPlugin
{
    String getIdentifier();

    String getName();

    String getDescription();

    String getTechnologyType();
}
