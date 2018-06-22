/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Instances of this class declare web pages used by the Newton user interface,
 * and provide the business logic that allows them to operate.
 *
 * @author Blair Butterworth
 */
@Controller
@RequestMapping("/")
@SuppressWarnings("unused")
public class ServletController
{
    public ServletController(){
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(ModelMap model)
    {
        return "redirect:/projects";
    }
}