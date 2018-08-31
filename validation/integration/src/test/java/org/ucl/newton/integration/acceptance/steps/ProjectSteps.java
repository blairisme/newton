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
import org.openqa.selenium.interactions.Actions;
import org.ucl.newton.common.network.RestException;
import org.ucl.newton.integration.acceptance.common.WebDriverUtils;
import org.ucl.newton.integration.acceptance.gherkin.Project;
import org.ucl.newton.integration.acceptance.newton.NewtonServer;
import org.ucl.newton.integration.acceptance.newton.project.ProjectDto;
import org.ucl.newton.integration.acceptance.newton.project.ProjectService;
import org.ucl.newton.integration.acceptance.newton.user.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Cucumber steps that drive the project user interface.
 *
 * @author Blair Butterworth
 * @author John Wilkie
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

    @Given("^the user \"(.*)\" has role \"(.*)\"$")
    public void onProjectsPage(String user, String role) throws RestException{
        UserService userService = newton.getUserService();
        userService.setRole(user, role);
    }

    @Given("^the user is on the settings page for project \"(.*)\"$")
    public void onSettingsPage(String projectIdent) {
        driver.get("http://localhost:9090/project/" + projectIdent + "/settings");
    }

    @Given("^the user is on the new project page$")
    public void navigateToNewProject() {
        driver.get("http://localhost:9090/project/new");
    }

    @Given("^user has already created a project with name \"(.*)\"$")
    public void createProject(String name) {
        navigateToNewProject();
        List<Project> projects = new ArrayList<>();
        projects.add(new Project("test-project", name, "Description ", "", "", ""));
        enterProjectDetails(projects);
        clickCreateProjectButton();
    }

    @When("^the user browses to the projects page$")
    public void navigateToProjects() {
        driver.get("http://localhost:9090/projects");
    }

    @When("^the user clicks the new project button$")
    public void clickNewProjectButton() {
        WebElement newProjectButton = driver.findElement(By.id("newProjectBtn"));
        newProjectButton.click();
    }

    @When("^the user clicks the create project button$")
    public void clickCreateProjectButton() {
        WebElement newProjectButton = driver.findElement(By.id("createProjectBtn"));
        newProjectButton.click();
    }

    @When("^the user enters the project details:$")
    public void enterProjectDetails(List<Project> projects) {
        Project project = projects.get(0);

        driver.findElement(By.id("projectNameInput")).sendKeys(project.getName());
        driver.findElement(By.id("projectDescriptionInput")).sendKeys(project.getDescription());
    }

    @When("^the delete project button is pressed$")
    public void pressDeleteProjectButton() {
        driver.findElement(By.className("delete-button")).click();
    }

    @When("^the user changes the description to \"(.*)\"$")
    public void changeDescription(String newDescription) {
        driver.findElement(By.id("projectDescriptionInput")).sendKeys(newDescription);
    }

    @When("^the update button is pressed$")
    public void pressUpdateButton() {
        driver.findElement(By.className("update-button")).click();
    }

    @Then("^the project list should contain the following projects:$")
    public void assertProjects(List<Project> projects) {
        Assert.assertEquals("http://localhost:9090/projects", driver.getCurrentUrl());
        List<WebElement> elements = driver.findElements(By.className("project-name"));

        List<String> actualNames = elements.stream().map(WebDriverUtils::getSpanText).collect(Collectors.toList());
        List<String> expectedNames = projects.stream().map(Project::getName).collect(Collectors.toList());

        Assert.assertEquals(expectedNames.size(), actualNames.size());
        Assert.assertTrue(actualNames.containsAll(expectedNames));
    }

    @Then("^the user should be on the new project page$")
    public void assertNewProjectsPage() {
        Assert.assertEquals("http://localhost:9090/project/new", driver.getCurrentUrl());
    }

    @Then("^the delete button should be disabled$")
    public void assertDeleteButtonDisabled() {
        WebElement deleteButton = driver.findElement(By.className("delete-button"));
        Assert.assertFalse(deleteButton.isEnabled());
    }

    @Then("^a project updated message should be shown$")
    public void assertUpdateMessageShowing() {
        WebElement successAlert = driver.findElement(By.className("alert-success"));
        Assert.assertTrue(successAlert.isDisplayed());
    }

    @Then("^a warning message should be shown$")
    public void assertWarningMessageshowing() {
        WebElement dangerAlert = driver.findElement(By.className("alert-danger"));
        Assert.assertTrue(dangerAlert.isDisplayed());
    }

    private boolean hasClass(WebElement element, String classRequired) {
        String classes = element.getAttribute("class");
        for (String c : classes.split(" ")) {
            if (c.equals(classRequired)) {
                return true;
            }
        }
        return false;
    }

}
