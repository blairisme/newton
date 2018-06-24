/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */
package org.ucl.newton.service;

import org.junit.Assert;
import org.junit.Test;

public class ExperimentServiceTest
{
    @Test
    public void getExperimentsTest() {
        ExperimentService experimentService = new ExperimentService();
        Assert.assertNotNull(experimentService.getExperiments());
    }
}