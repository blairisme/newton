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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.ucl.newton.api.user.UserDto;
import org.ucl.newton.framework.User;
import org.ucl.newton.service.authentication.AuthenticationService;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Instances of this class provide an MVC controller for web pages used to
 * display the sign in page.
 *
 * @author Blair Butterworth
 * @author John Wilkie
 */
@Controller
@SuppressWarnings("unused")
public class AuthenticationController
{
    private UserService userService;
    private AuthenticationService authService;

    @Inject
    public AuthenticationController(UserService userService, AuthenticationService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @RequestMapping(value = "/login")
    public String login(@RequestParam(value = "error", defaultValue = "false")boolean error, ModelMap model) {
        model.addAttribute("error", error);
        return "auth/login";
    }

    @RequestMapping(value="/signup", method = RequestMethod.GET)
    public String register(ModelMap model) {
        model.addAttribute("user", new UserDto());
        return "auth/signup";
    }

    @RequestMapping(value = "/access_denied")
    public String accessDenied(ModelMap model) {
        return "auth/denied";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") @Valid UserDto userDto,
                               BindingResult result) {

        User existing = userService.getUserByEmail(userDto.getEmail());
        if(existing != null) {
            result.rejectValue("email", null, "This email address has already been used to register an account");
        }

        if(result.hasErrors()) {
            return "auth/signup";
        }

        userService.addUser(userDto);
        User newlyCreatedUser = userService.getUserByEmail(userDto.getEmail());
        authService.save(userDto, newlyCreatedUser);
        return "redirect:/projects";
    }

}
