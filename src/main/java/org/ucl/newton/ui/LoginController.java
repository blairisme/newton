/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Instances of this class provide an MVC controller for web pages used to
 * display the sign in page.
 *
 * @author Blair Butterworth
 */
@Controller
@Scope("session")
@RequestMapping("/")
@SuppressWarnings("unused")
public class LoginController
{
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        return "Login";
    }
}
