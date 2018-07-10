package org.ucl.newton.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * Instances of this class provide an MVC controller for web pages used to
 * list and manage projects.
 *
 * @author Xiaolong Chen
 */
@Controller
@Scope("session")
@SuppressWarnings("unused")
public class PluginController {
    @Inject
    public PluginController(){ }

    @RequestMapping(value = "/weatherSetting", method = RequestMethod.GET)
    public String list(ModelMap model) {
        return "plugin/weatherSetting";
    }
}
