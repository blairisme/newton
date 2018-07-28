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
import org.ucl.newton.common.archive.ZipUtils;
import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.service.experiment.ExperimentService;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Instances of this class provide experiment data and methods via REST.
 *
 * @author Blair Butterworth
 */
@Controller
@SuppressWarnings("unused")
public class ExperimentApi
{
    private ExperimentService experimentService;
    private ApplicationStorage applicationStorage;

    @Inject
    public ExperimentApi(ApplicationStorage applicationStorage, ExperimentService experimentService) {
        this.applicationStorage = applicationStorage;
        this.experimentService = experimentService;
    }

    @RequestMapping(value = "/api/experiment/{experimentId}/repository", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getRepository(@PathVariable("experimentId") String experimentId) throws IOException {
        Path experimentsPath = applicationStorage.getExperimentDirectory();
        Path experimentPath = experimentsPath.resolve(experimentId);
        Path repositoryPath = experimentPath.resolve("repository");

        Path tempPath = applicationStorage.getTempDirectory();
        Path archive = tempPath.resolve(UUID.randomUUID().toString() + ".zip");
        FileUtils.createNew(archive.toFile());

        ZipUtils.zip(repositoryPath, archive);
        return new FileSystemResource(archive.toFile());
    }
}
