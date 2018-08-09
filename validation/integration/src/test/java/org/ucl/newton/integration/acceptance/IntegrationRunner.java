/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.acceptance;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Test suite containing Cucumber based acceptance tests.
 *
 * @author Blair Butterworth
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        features={"classpath:features/"},
        glue = { "org.ucl.newton.integration.acceptance.steps"})
public class IntegrationRunner
{
}
