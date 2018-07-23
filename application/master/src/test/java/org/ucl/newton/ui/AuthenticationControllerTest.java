/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.ucl.newton.service.authentication.AuthenticationService;
import org.ucl.newton.service.project.ProjectRepository;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;

public class AuthenticationControllerTest
{

    @Inject
    private UserService service;

    @Inject
    private AuthenticationService auth;

    @Test
    public void frontPageTest(){
        AuthenticationController controller = new AuthenticationController(service, auth);
        Assert.assertEquals("auth/login", controller.login(false, new ModelMap()));
    }
}
