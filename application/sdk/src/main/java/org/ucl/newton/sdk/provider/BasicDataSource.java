/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.sdk.provider;

/**
 * A simple {@link DataSource} implementation using a parent, id and name
 * provided on construction.
 *
 * @author Blair Butterworth
 */
public class BasicDataSource implements DataSource
{
    private DataProvider provider;
    private String identifier;
    private String name;

    public BasicDataSource() {
    }

    public BasicDataSource(DataProvider provider, String identifier, String name) {
        this.identifier = identifier;
        this.name = name;
        this.provider = provider;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DataProvider getProvider() {
        return provider;
    }
}
