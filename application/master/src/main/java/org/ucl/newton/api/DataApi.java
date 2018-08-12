/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.api;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.ucl.newton.framework.DataPermission;
import org.ucl.newton.framework.User;
import org.ucl.newton.sdk.provider.DataSource;
import org.ucl.newton.service.data.DataPermissionService;
import org.ucl.newton.service.data.DataService;
import org.ucl.newton.service.data.DataStorageProvider;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;
import java.nio.file.Path;

/**
 * Provides an API allowing clients to access to data sources.
 *
 * @author Blair Butterworth
 * @author John Wilkie
 */
@Controller
@SuppressWarnings("unused")
public class DataApi
{
    private DataService dataService;
    private DataPermissionService dataPermissionService;
    private UserService userService;

    @Inject
    public DataApi(
        DataService dataService,
        DataPermissionService dataPermissionService,
        UserService userSerive)
    {
        this.dataService = dataService;
        this.dataPermissionService = dataPermissionService;
        this.userService = userSerive;
    }

    @RequestMapping(value = "/api/data/{dataSourceId}", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getDataSource(@PathVariable("dataSourceId") String dataSourceId) {
        DataSource dataSource = dataService.getDataSource(dataSourceId);
        DataStorageProvider dataStorage = dataService.getDataStorage(dataSource.getProvider());
        Path dataPath = dataStorage.getPath(dataSource);
        return new FileSystemResource(dataPath.toFile());
    }

    @RequestMapping(value = "/api/data/permissions/{dsIdent}/permission", method = RequestMethod.GET)
    @ResponseBody
    public DataPermission getPermission(@PathVariable("dsIdent") String dsIdent) {
        return dataPermissionService.getPermissionByDataSourceName(dsIdent);
    }

    @RequestMapping(value = "/api/data/permissions/{ident}/remove", method = RequestMethod.POST)
    @ResponseBody
    public  boolean removeGrantedPermission(
            @PathVariable("ident") String dataSourceIdent,
            @RequestParam String user)
    {
        try {
            User userFound = userService.getUserByEmail(user);
            dataPermissionService.removeGrantedPermission(dataSourceIdent, userFound);
        } catch (Throwable e) {
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/api/data/permissions/{ident}/add", method = RequestMethod.POST)
    @ResponseBody
    public  boolean addGrantedPermission(
            @PathVariable("ident") String dataSourceIdent,
            @RequestParam String user)
    {
        try {
            User userFound = userService.getUserByEmail(user);
            dataPermissionService.addGrantedPermission(dataSourceIdent, userFound);
        } catch (Throwable e) {
            return false;
        }
        return true;
    }
}
