/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.experiment;

import org.ucl.newton.framework.Experiment;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Instances of this interface provide access to experiment data.
 *
 * @author Blair Butterworth
 */
@Named
public class ExperimentService
{
    private Collection<Experiment> experiments;

    public ExperimentService() {
        experiments = new ArrayList<>();
        experiments.add(new Experiment("1","first experiment"));
        experiments.add(new Experiment("2","second experiment"));
        experiments.add(new Experiment("3","third experiment"));
    }
    public Collection<Experiment> getExperiments() {
        return experiments;
    }
}
