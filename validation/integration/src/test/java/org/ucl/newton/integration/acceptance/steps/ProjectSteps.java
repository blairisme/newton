/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.acceptance.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.ucl.newton.common.network.RestException;
import org.ucl.newton.integration.acceptance.common.WebDriverUtils;
import org.ucl.newton.integration.acceptance.gherkin.Project;
import org.ucl.newton.integration.acceptance.newton.NewtonServer;
import org.ucl.newton.integration.acceptance.newton.project.ProjectDto;
import org.ucl.newton.integration.acceptance.newton.project.ProjectService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    public ProjectSteps(StepContext context) {
        driver = context.getDriver();
        newton = context.getNewton();
    }

    @Given("^the system has the following projects:$")
    public void initializeUsers(List<Project> projects) throws RestException {
        ProjectService projectService = newton.getProjectService();

        Collection<ProjectDto> currentProjects = projectService.getProjects();
        projectService.removeProjects(currentProjects);

        Collection<ProjectDto> newProjects = projects.stream().map(Project::asProjectDto).collect(Collectors.toList());
        projectService.addProjects(newProjects);
    }

    @When("^the user browses to the projects page$")
    public void navigateToProjects() {
        driver.get("http://localhost:9090/projects");
    }

    @Then("^the project list should contain the following projects:$")
    public void assertProjectCount(List<Project> projects) {
        Assert.assertEquals("http://localhost:9090/projects", driver.getCurrentUrl());
        List<WebElement> elements = driver.findElements(By.className("project-name"));

        List<String> actualNames = elements.stream().map(WebDriverUtils::getSpanText).collect(Collectors.toList());
        List<String> expectedNames = projects.stream().map(Project::getName).collect(Collectors.toList());

        Assert.assertEquals(expectedNames.size(), actualNames.size());
        Assert.assertTrue(actualNames.containsAll(expectedNames));
    }
}
