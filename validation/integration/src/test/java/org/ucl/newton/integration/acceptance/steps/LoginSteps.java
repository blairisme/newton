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
import org.ucl.newton.integration.acceptance.gherkin.Project;
import org.ucl.newton.integration.acceptance.gherkin.User;
import org.ucl.newton.integration.acceptance.newton.NewtonServer;
import org.ucl.newton.integration.acceptance.newton.user.UserDto;
import org.ucl.newton.integration.acceptance.newton.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

import static org.ucl.newton.integration.acceptance.common.WebDriverUtils.elementExists;

/**
 * Cucumber steps that drive the login user interface.
 *
 * @author Blair Butterworth
 */
@SuppressWarnings("unused")
public class LoginSteps
{
    private WebDriver driver;
    private NewtonServer newton;

    public LoginSteps(StepContext context) {
        driver = context.getDriver();
        newton = context.getNewton();
    }

    @Given("^the system has the following users:$")
    public void initializeUsers(List<User> userDetails) throws RestException {
        List<UserDto> users = userDetails.stream().map(User::asUserDto).collect(Collectors.toList());
        UserService userService = newton.getUserService();
        userService.removeUsers(users);
        userService.addUsers(users);
    }

    @When("^the user is shown the login page$")
    public void navigateToLogin() {
        driver.get("http://localhost:9090/login");
    }

    @When("^the user enters \"(.*)\" as their username and \"(.*)\" as their password$")
    public void enterDetails(String username, String password) {
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(password);
    }

    @When("^the user selects the signin option$")
    public void selectSignin() {
        WebElement signInButton = driver.findElement(By.id("signin"));
        signInButton.click();
    }

    @When("^the user logs in as \"(.*)\" with \"(.*)\" as their password")
    public void performLogin(String username, String password) {
        navigateToLogin();
        enterDetails(username, password);
        selectSignin();
    }

    @Then("^the user should be shown the project list$")
    public void assertShownProjects() {
        Assert.assertEquals("http://localhost:9090/projects", driver.getCurrentUrl());
    }

    @Then("^the user should be shown an authentication error$")
    public void assertShownError() {
        Assert.assertEquals("http://localhost:9090/login?error=true", driver.getCurrentUrl());
        Assert.assertTrue(elementExists(driver, By.id("authentication_error")));
    }
}
