/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.acceptance.steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.ucl.newton.integration.acceptance.common.WebDriverUtils;
import org.ucl.newton.integration.acceptance.newton.NewtonServer;

/**
 * Contains state common to scenario steps.
 *
 * @author Blair Butterworth
 */
public class StepContext
{
    private WebDriver driver;
    private NewtonServer newton;

    public StepContext() {
        newton = new NewtonServer();
        driver = new HtmlUnitDriver(true);
        WebDriverUtils.disableLogging(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public NewtonServer getNewton() {
        return newton;
    }
}
