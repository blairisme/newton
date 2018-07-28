/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.network;

import org.ucl.newton.bridge.ExecutionCoordinator;
import org.ucl.newton.slave.application.ApplicationPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

/**
 * Creates new {@link ExecutionCoordinator} objects, configured using
 * {@link ApplicationPreferences}.
 *
 * @author Blair Butterworth
 * @author Ziad Al Halabi
 */
@Named
public class MasterServerFactory implements Provider<ExecutionCoordinator>
{
    private ApplicationPreferences preferences;
    private Provider<ExecutionCoordinator> clientFactory;

    @Inject
    public MasterServerFactory(
        ApplicationPreferences preferences,
        Provider<ExecutionCoordinator> clientFactory)
    {
        this.preferences = preferences;
        this.clientFactory = clientFactory;
    }

    @Override
    public ExecutionCoordinator get() {
        ExecutionCoordinator client = clientFactory.get();
        client.setHost(preferences.getMasterHost());
        client.setPort(preferences.getMasterPort());
        return client;
    }
}
