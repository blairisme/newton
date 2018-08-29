package org.ucl.newton.integration.acceptance.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.ucl.newton.integration.acceptance.newton.NewtonServer;

/**
 * Cucumber steps that drive the sign up user interface.
 *
 * @author John Wilkie
 */
@SuppressWarnings("unused")
public class SignupSteps {

    private WebDriver driver;
    private NewtonServer newton;

    public SignupSteps(StepContext context) {
        driver = context.getDriver();
        newton = context.getNewton();
    }

    @Given("^the user is on the signup page$")
    public void navigateToSignup() {
        driver.get("http://localhost:9090/signup");
    }

    @When("^the user enters \"(.*)\" as their full name, \"(.*)\" as their email, \"(.*)\" as their password and \"(.*)\" as the reentered password$")
    public void completeSignin(String name, String email, String password, String passwordAgain) {
        driver.findElement(By.id("fullName")).sendKeys(name);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("passwordInput")).sendKeys(password);
        driver.findElement(By.id("confirmPassword")).sendKeys(passwordAgain);
    }

    @And("^presses the register button$")
    public void clickRegister() {
        driver.findElement(By.id("submitBtn")).click();
    }

    @Then("^the user should be shown the log in page$")
    public void assertShownProjects() {
        Assert.assertEquals("http://localhost:9090/login", driver.getCurrentUrl());
    }

    @Then("^the register button should be disabled$")
    public void assertRegisterbuttonDisabled() {
        WebElement registerbutton = driver.findElement(By.id("submitBtn"));
        Assert.assertFalse(registerbutton.isEnabled());
    }

    @Then("^the user should still be on the sign up page$")
    public void assertOnSignUpPage() {
        Assert.assertEquals("http://localhost:9090/signup", driver.getCurrentUrl());
    }

    @Then("^be shown a warning$")
    public void assertWarningshowing() {
        WebElement alertMessage = driver.findElement(By.id("emailAlert"));
        Assert.assertTrue(alertMessage.isDisplayed());
    }

}
