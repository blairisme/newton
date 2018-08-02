/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.List;

import static org.ucl.newton.integration.WebDriverUtils.elementExists;

@SuppressWarnings("unused")
public class LoginSteps
{
    private WebDriver driver;

    @Before
    public void setup() {
        driver = new HtmlUnitDriver(true);
    }

    @Given("^the system has the following users:$")
    public void initializeUsers(List<User> users) {

    }

    @When("^the user is shown the login page$")
    public void showPage() {
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

    @Then("^the user should be shown the project list$")
    public void assertShownProjects() { ;
        Assert.assertEquals("http://localhost:9090/projects", driver.getCurrentUrl());
    }

    @Then("^the user should be shown an authentication error$")
    public void assertShownError() {
        Assert.assertEquals("http://localhost:9090/login?error=true", driver.getCurrentUrl());
        Assert.assertTrue(elementExists(driver, By.id("authentication_error")));
    }
}
