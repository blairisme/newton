/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.acceptance.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.ucl.newton.common.network.RestException;
import org.ucl.newton.integration.acceptance.common.WebDriverUtils;
import org.ucl.newton.integration.acceptance.gherkin.Experiment;
import org.ucl.newton.integration.acceptance.newton.NewtonServer;
import org.ucl.newton.integration.acceptance.newton.experiment.ExperimentDto;
import org.ucl.newton.integration.acceptance.newton.experiment.ExperimentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Cucumber steps that drive the experiment user interface.
 *
 * @author Blair Butterworth
 * @author John Wilkie
 */
@SuppressWarnings("unused")
public class ExperimentSteps
{
    private WebDriver driver;
    private NewtonServer newton;

    public ExperimentSteps(StepContext context) {
        driver = context.getDriver();
        newton = context.getNewton();
    }

    @Given("^the system has the following experiments:$")
    public void initializeExperiments(List<Experiment> experiments) throws RestException {
        Collection<ExperimentDto> dtos = experiments.stream().map(Experiment::asExperimentDto).collect(toList());
        ExperimentService experimentService = newton.getExperimentService();
        experimentService.removeExperiments(dtos);
        experimentService.addExperiments(dtos);
    }

    @Given("^the user is on the new experiment page for (.*)$")
    public void onNewExperimentPage(String project) {
        driver.get("http://localhost:9090/project/" + project + "/new");
    }

    @Given("^the user is on the experiment settings page for experiment \"(.*)\" in project \"(.*)\"$")
    public void navigateToExperimentSettings(String experimentIdent, String projectIdent) {
        driver.get("http://localhost:9090/project/" + projectIdent + "/" + experimentIdent + "/setup");
    }

    @Given("^the user has already created an experiment with name \"(.*)\" for project \"(.*)\"$")
    public void createProject(String experimentName, String projectIdent) {
        List<Experiment> experiments = new ArrayList<>();
        experiments.add(new Experiment("test-experiment", experimentName, "", "", ""));
        onNewExperimentPage(projectIdent);
        enterExperimentDetails(experiments);
        clickCreateNewExperiment();
    }

    @When("^the user browses to the experiments page for (.*)$")
    public void navigateToExperimentList(String project) {
        driver.get("http://localhost:9090/project/" + project);
    }

    @When("^the user browses to the experiment details page for (.*) \\((.*)\\)$")
    public void navigateToExperimentDetails(String experiment, String project) {
        driver.get("http://localhost:9090/project/" + project + "/" + experiment);
    }

    @When("^the user runs the experiment$")
    public void runExperiment() {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.matches("http://localhost:9090/project/(.*)/(.*)"));

        WebElement runExperiment = driver.findElement(By.id("run-experiment"));
        runExperiment.click();

        WebDriverUtils.waitForElementToExist(driver, By.id("title"), 1000, 10);
    }

    @And("^clicks the new experiment button$")
    public void clickNewExperimentButton() {
        WebElement newExperimentButton = driver.findElement(By.id("newExpBtn"));
        newExperimentButton.click();
    }

    @When("^the user enters the experiment details:$")
    public void enterExperimentDetails(List<Experiment> experiments) {
        Experiment experiment3 = experiments.get(0);

        driver.findElement(By.id("experimentNameInput")).sendKeys(experiment3.getName());
        driver.findElement(By.id("experimentDescInput")).sendKeys(experiment3.getDescription());

    }

    @And("^clicks the create experiment button$")
    public void clickCreateNewExperiment() {
        WebElement createExperimentButton = driver.findElement(By.id("createBtn"));
        createExperimentButton.click();
    }

    @When("^the user presses the delete button$")
    public void pressDeleteButton() {
        driver.findElement(By.className("delete-button")).click();
    }

    @When("^the user changes the experiment description to \"(.*)\"$")
    public void setExperimentDescription(String newDescription) {
        driver.findElement(By.id("experimentDescInput")).sendKeys(newDescription);
    }

    @And("^presses the experiment update button$")
    public void pressUpdateExperiment() {
        driver.findElement(By.className("update-button")).click();
    }

    @Then("^the experiment list should contain the following experiments:$")
    public void assertExperimentList(List<Experiment> experiments) {
        String project = experiments.get(0).getProject();

        Assert.assertEquals("http://localhost:9090/project/" + project, driver.getCurrentUrl());
        List<WebElement> elements = driver.findElements(By.className("experiment-name"));

        List<String> actualNames = elements.stream().map(WebDriverUtils::getSpanText).collect(Collectors.toList());
        List<String> expectedNames = experiments.stream().map(Experiment::getName).collect(Collectors.toList());

        Assert.assertEquals(expectedNames.size(), actualNames.size());
        Assert.assertTrue(actualNames.containsAll(expectedNames));
    }

    @Then("^the experiment details page should contain the following:$")
    public void assertExperimentDetails(List<Experiment> experiments) {
        Experiment experiment = experiments.get(0);

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("http://localhost:9090/project/" + experiment.getProject() + "/" + experiment.getIdentifier(), currentUrl);

        WebElement title = driver.findElement(By.id("title"));
        Assert.assertEquals(experiment.getName(), title.getText());
    }

    @Then("^the user should be on the new experiment page for (.*)$")
    public void assertNewExperimentPage(String project) {
        Assert.assertEquals("http://localhost:9090/project/" + project +"/new", driver.getCurrentUrl());
    }

    @Then("^the delete button should be deactivated$")
    public void assertDeleteDeactivated() {
        Assert.assertFalse(driver.findElement(By.className("delete-button")).isEnabled());
    }

    @Then("^the user should be on the experiments page for \"(.*)\"$")
    public void assertOnExperimentsPage(String projectIdent) {
        Assert.assertEquals("http://localhost:9090/project/" + projectIdent, driver.getCurrentUrl());
    }

    @Then("^the user should be shown a successful update message$")
    public void assertSuccessfulUpdate() {
        WebElement successMessage = driver.findElement(By.className("alert-success"));
        Assert.assertEquals("Update was successful", successMessage.getText());
    }

    @Then("^a warning message should be shown for a duplicate experiment name \"(.*)\"$")
    public void assertWarningMessage(String experimentName) {
        WebElement alertMessage = driver.findElement(By.className("alert-danger"));
        Assert.assertEquals("Could not create experiment as an experiment with name " + experimentName + " already exists", alertMessage.getText());
    }

}

