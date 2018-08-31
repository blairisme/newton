package org.ucl.newton.integration.acceptance.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Cucumber steps that drive the user user interface.
 *
 * @author John Wilkie
 */
public class UserSteps {

    private WebDriver driver;

    public UserSteps(StepContext context) {
        driver = context.getDriver();
    }

    @When("^the user navigates to their profile page$")
    public void navigateToProfile() {
        driver.get("http://localhost:9090/profile");
    }

    @And("^presses the delete button$")
    public void pressDeleteButton() {
        driver.findElement(By.className("delete-btn")).click();
    }

    @Then("^they should be on the profile page with details name \"(.*)\", email \"(.*)\", they own \"(.*)\" projects and are a member of \"(.*)\" projects$")
    public void assertCorrectProfilePage(String name, String email, int numProjects, int numMember) {
        Assert.assertEquals("http://localhost:9090/profile", driver.getCurrentUrl());
        Assert.assertEquals("Name: " + name, driver.findElement(By.id("profileName")).getText());
        Assert.assertEquals("Email: " + email, driver.findElement(By.id("profileEmail")).getText());
        Assert.assertEquals("Number of projects owned: " + numProjects, driver.findElement(By.id("profileNumProjectsOwned")).getText());
        Assert.assertEquals("Number of projects a member of: " + numMember, driver.findElement(By.id("profileNumProjectsMember")).getText());
        Assert.assertEquals("Number of projects starred: 0", driver.findElement(By.id("profileNumProjectsStarred")).getText());
    }

    @Then("^the user should be deleted and on the landing page$")
    public void assertOnLandingPage() {
        Assert.assertEquals("http://localhost:9090/", driver.getCurrentUrl());
    }

}
