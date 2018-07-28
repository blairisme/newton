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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ucl.newton.application.system.ApplicationStorage;

import javax.inject.Inject;
import java.nio.file.Path;

/**
 * Provides an API allowing clients to access to data sources.
 *
 * @author Blair Butterworth
 */
@Controller
@SuppressWarnings("unused")
public class DataApi
{
    private ApplicationStorage applicationStorage;

    @Inject
    public DataApi(ApplicationStorage applicationStorage) {
        this.applicationStorage = applicationStorage;
    }

    @RequestMapping(value = "/api/data/{dataSourceId}", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getDataSource(@PathVariable("dataSourceId") String dataSourceId) {
        Path directory = applicationStorage.getDataDirectory();
        Path dataSource = directory.resolve(dataSourceId);
        return new FileSystemResource(dataSource.toFile());
    }
}
