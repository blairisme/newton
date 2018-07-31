/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.sdk.data;

/**
 * Represents a data set containing information of use to users of the Newton
 * system. <code>DataSources</code> are created and maintained by
 * {@link DataProvider DataProviders}.
 *
 * @author Blair Butterworth
 */
public interface DataSource
{
    String getIdentifier();

    String getName();

    DataProvider getProvider();
}
