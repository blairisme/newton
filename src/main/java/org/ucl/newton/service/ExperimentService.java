/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service;

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
        this.experiments = new ArrayList<>();
    }

    public Collection<Experiment> getExperiments() {
        return experiments;
    }
}
