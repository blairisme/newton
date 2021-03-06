/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.framework.User;
import org.ucl.newton.sdk.plugin.NewtonPlugin;
import org.ucl.newton.sdk.processor.DataProcessor;
import org.ucl.newton.sdk.provider.DataProvider;
import org.ucl.newton.sdk.publisher.DataPublisher;
import org.ucl.newton.service.data.DataPermissionService;
import org.ucl.newton.service.plugin.Plugin;
import org.ucl.newton.service.plugin.PluginService;
import org.ucl.newton.service.project.ProjectService;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.UUID;
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
    private PluginService pluginService;
    private ProjectService projectService;
    private ApplicationStorage applicationStorage;
    private DataPermissionService dataPermissionService;

    @Inject
    public SettingsController(
        UserService userService,
        DataPermissionService dataPermissionService,
        PluginService pluginService,
        ProjectService projectService,
        ApplicationStorage applicationStorage)
    {
        this.userService = userService;
        this.dataPermissionService = dataPermissionService;
        this.pluginService = pluginService;
        this.projectService = projectService;
        this.applicationStorage = applicationStorage;
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
        Collection<DataProvider> providers = pluginService.getDataProviders();
        Collection<DataProcessor> processors = pluginService.getDataProcessors();
        Collection<DataPublisher> publishers = pluginService.getDataPublishers();

        providers.forEach(plugin -> model.addAllAttributes(plugin.getConfiguration().getValues()));
        processors.forEach(plugin -> model.addAllAttributes(plugin.getConfiguration().getValues()));
        publishers.forEach(plugin -> model.addAllAttributes(plugin.getConfiguration().getValues()));

        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("providers", providers);
        model.addAttribute("processors", processors);
        model.addAttribute("publishers", publishers);

        return "settings/plugins";
    }

    @RequestMapping(value="/settings/plugins/update", method=RequestMethod.POST)
    public String updatePlugins(@RequestBody MultiValueMap<String, String> formData) {
        Consumer<NewtonPlugin> updateConfiguration = plugin -> plugin.getConfiguration().setValues(formData);
        pluginService.getDataProviders().forEach(updateConfiguration);
        pluginService.getDataProcessors().forEach(updateConfiguration);
        pluginService.getDataPublishers().forEach(updateConfiguration);
        return "redirect:/settings/plugins";
    }

    @RequestMapping(value="/settings/plugins/add", method=RequestMethod.POST)
    public String addPlugin(@RequestParam MultipartFile pluginData) throws IOException {
        Path pluginsDirectory = applicationStorage.getPluginsDirectory();
        Path pluginPath = pluginsDirectory.resolve(UUID.randomUUID().toString() + ".jar");

        try(InputStream inputStream = pluginData.getInputStream();
            OutputStream outputStream = new FileOutputStream(pluginPath.toFile())) {
            IOUtils.copy(inputStream, outputStream);
        }
        Plugin plugin = new Plugin(pluginPath.toString());
        pluginService.addPlugin(plugin);

        return "redirect:/settings/plugins";
    }

    @RequestMapping(value="/settings/plugins/delete")
    public String deletePlugin(@RequestParam String pluginId) throws IOException {
        Plugin plugin = pluginService.getPlugin(pluginId);
        if (plugin != null) {
            pluginService.removePlugin(plugin);
            FileUtils.deleteQuietly(plugin.asResource().getFile());
        }
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
            return "redirect:/logout";
        } catch (Throwable e) {
            return "redirect:/profile";
        }
    }
}
