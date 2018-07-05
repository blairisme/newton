/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.execution;

import org.ucl.newton.framework.Experiment;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class run experiments.
 *
 * @author Blair Butterworth
 */
@Named
public class ExecutionService
{
    private List<ExecutionNode> executionNodes;

    public ExecutionService() {
        executionNodes = new ArrayList<>();
        executionNodes.add(new ExecutionNode());
    }

    public void run(Experiment experiment){
        executionNodes.get(0).schedule(null);
    }
}
