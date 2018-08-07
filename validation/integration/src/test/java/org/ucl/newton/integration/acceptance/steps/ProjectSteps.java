/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.acceptance.steps;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.ucl.newton.common.network.RestException;
import org.ucl.newton.integration.acceptance.newton.NewtonServer;
import org.ucl.newton.integration.acceptance.newton.project.Project;
import org.ucl.newton.integration.acceptance.newton.project.ProjectService;
import org.ucl.newton.integration.acceptance.newton.user.User;
import org.ucl.newton.integration.acceptance.newton.user.UserService;

import java.util.List;

/**
 * Cucumber steps that drive the project user interface.
 *
 * @author Blair Butterworth
 */
@SuppressWarnings("unused")
public class ProjectSteps
{
    private WebDriver driver;
    private NewtonServer newton;

    @Before
    public void setup() {
        driver = new HtmlUnitDriver(true);
        newton = new NewtonServer();
    }

    @Given("^the system has the following projects:$")
    public void initializeUsers(List<Project> projects) throws RestException {
        ProjectService projectService = newton.getProjectService();
        //userService.removeUsers();
        //userService.addUsers(users);
    }

}
