/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.engine;

import org.ucl.newton.application.system.ApplicationPreferences;
import org.ucl.newton.bridge.ExecutionNode;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

/**
 * Creates a new {@link ExecutionNode} through which execution requests can be
 * made to Newton slaves.
 *
 * @author Blair Butterworth
 */
@Named
public class ExecutionRemoteNode implements Provider<ExecutionNode>
{
    private ApplicationPreferences preferences;
    private Provider<ExecutionNode> nodeFactory;

    @Inject
    public ExecutionRemoteNode(ApplicationPreferences preferences, Provider<ExecutionNode> nodeFactory) {
        this.preferences = preferences;
        this.nodeFactory = nodeFactory;
    }

    @Override
    public ExecutionNode get() {
        ExecutionNode executionNode = nodeFactory.get();
        executionNode.setHost(preferences.getSlaveHost());
        executionNode.setPort(preferences.getSlavePort());
        return executionNode;
    }
}
