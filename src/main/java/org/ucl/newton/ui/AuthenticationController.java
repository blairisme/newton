/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Instances of this class provide an MVC controller for web pages used to
 * display the sign in page.
 *
 * @author Blair Butterworth
 */
@Controller
@SuppressWarnings("unused")
public class AuthenticationController
{
    @RequestMapping(value = "/login")
    public String login(@RequestParam(value = "error", defaultValue = "false")boolean error, ModelMap model) {
        model.addAttribute("error", error);
        return "auth/login";
    }

    @RequestMapping(value = "/access_denied")
    public String accessDenied(ModelMap model) {
        return "auth/denied";
    }
}
