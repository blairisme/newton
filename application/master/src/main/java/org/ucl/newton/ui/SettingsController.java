/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.ucl.newton.framework.User;
import org.ucl.newton.sdk.plugin.NewtonPlugin;
import org.ucl.newton.service.data.DataPermissionService;
import org.ucl.newton.service.plugin.PluginService;
import org.ucl.newton.service.project.ProjectService;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.function.Consumer;

/**
 * Instances of this class provide an MVC controller for web pages used to
 * display settings, both for the system and for the users profile.
 *
 * @author Blair Butterworth
 * @author John Wilkie
 */
@Controller
@SuppressWarnings("unused")
public class SettingsController
{
    private UserService userService;
    private DataPermissionService dataPermissionService;
    private PluginService pluginService;
    private ProjectService projectService;

    @Inject
    public SettingsController(
            UserService userService,
            DataPermissionService dataPermissionService,
            PluginService pluginService,
            ProjectService projectService)
    {
        this.userService = userService;
        this.dataPermissionService = dataPermissionService;
        this.pluginService = pluginService;
        this.projectService = projectService;
    }

    @RequestMapping(value = "/settings/roles", method = RequestMethod.GET)
    public String roles(ModelMap model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "settings/roles";
    }

    @RequestMapping(value = "/settings/data-permissions", method = RequestMethod.GET)
    public String dataPermissions(ModelMap model) {
        User user = userService.getAuthenticatedUser();
        model.addAttribute("user", user);
        model.addAttribute("ownedPermission", dataPermissionService.getPermissionsOwnedByUser(user));
        model.addAttribute("grantedPermission", dataPermissionService.getPermissionsGrantedToUser(user));
        return "settings/data-permissions";
    }

    @RequestMapping(value = "/settings/plugins", method = RequestMethod.GET)
    public String viewPlugins(ModelMap model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("providers", pluginService.getDataProviders());
        model.addAttribute("processors", pluginService.getDataProcessors());
        model.addAttribute("publishers", pluginService.getDataPublishers());
        return "settings/plugins";
    }

    @RequestMapping(value="/settings/plugins/update", method=RequestMethod.POST)
    public String updatePlugins(@RequestBody MultiValueMap<String, String> formData) {
        Consumer<NewtonPlugin> updateConfiguration = plugin -> plugin.getConfiguration().update(formData);
        pluginService.getDataProviders().forEach(updateConfiguration);
        pluginService.getDataProcessors().forEach(updateConfiguration);
        pluginService.getDataPublishers().forEach(updateConfiguration);
        return "redirect:/settings/plugins";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(ModelMap model) {
        User currentUser = userService.getAuthenticatedUser();
        model.addAttribute("user", currentUser);
        model.addAttribute("numProjectsAMemberOf", projectService.getProjects(currentUser).size());
        model.addAttribute("numOfProjectsOwned", projectService.getOwnedProjects(currentUser).size());
        model.addAttribute("numOfProjectsStarred", projectService.getStarredProjects(currentUser).size());
        return "settings/profile";
    }

    @RequestMapping(value = "/profile/delete", method = RequestMethod.POST)
    public String deleteUser() {
        try {
            userService.removeUser(userService.getAuthenticatedUser());
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                            .getRequest();
            new SecurityContextLogoutHandler().logout(request, null, null);
            return "main/landing";
        } catch (Throwable e) {
            return "redirect:/profile";
        }
    }
}
