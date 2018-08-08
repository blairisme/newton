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
import org.springframework.web.bind.annotation.RequestMethod;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;

/**
 * Instances of this class provide an MVC controller for web pages used to
 * display settings, both for the system and for the users profile.
 *
 * @author Blair Butterworth
 */
@Controller
@SuppressWarnings("unused")
public class SettingsController
{
    private UserService userService;

    @Inject
    public SettingsController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/settings/roles", method = RequestMethod.GET)
    public String roles(ModelMap model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "settings/roles";
    }

    @RequestMapping(value = "/settings/data-permissions", method = RequestMethod.GET)
    public String dataPermissions(ModelMap model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "settings/data-permissions";
    }

    @RequestMapping(value = "/settings/plugins", method = RequestMethod.GET)
    public String plugins(ModelMap model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "settings/plugins";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(ModelMap model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "settings/profile";
    }
}
