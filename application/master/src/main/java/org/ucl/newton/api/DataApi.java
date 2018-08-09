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
import org.ucl.newton.sdk.provider.DataSource;
import org.ucl.newton.service.data.DataService;
import org.ucl.newton.service.data.DataStorageProvider;

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
    private DataService dataService;

    @Inject
    public DataApi(DataService dataService) {
        this.dataService = dataService;
    }

    @RequestMapping(value = "/api/data/{dataSourceId}", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getDataSource(@PathVariable("dataSourceId") String dataSourceId) {
        DataSource dataSource = dataService.getDataSource(dataSourceId);
        DataStorageProvider dataStorage = dataService.getDataStorage(dataSource.getProvider());
        Path dataPath = dataStorage.getPath(dataSource);
        return new FileSystemResource(dataPath.toFile());
    }
}
