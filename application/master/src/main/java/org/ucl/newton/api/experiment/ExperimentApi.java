/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.api.experiment;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.common.archive.ZipUtils;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.StorageConfiguration;
import org.ucl.newton.service.experiment.ExperimentOperations;
import org.ucl.newton.service.experiment.ExperimentService;

import javax.inject.Inject;
import java.io.*;
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
    private ApplicationStorage applicationStorage;
    private ExperimentService experimentService;
    private ExperimentOperations experimentOperations;

    @Inject
    public ExperimentApi(
        ApplicationStorage applicationStorage,
        ExperimentService experimentService,
        ExperimentOperations experimentOperations)
    {
        this.applicationStorage = applicationStorage;
        this.experimentService = experimentService;
        this.experimentOperations = experimentOperations;
    }

    @RequestMapping(value = "/api/experiment", method = RequestMethod.POST)
    public void addExperiment(@RequestBody ExperimentDto experimentDto) {
        Experiment experiment = experimentOperations.createExperiment(experimentDto);
        experimentService.addExperiment(experiment);
    }

    @RequestMapping(value = "/api/experiment/{identifier}", method = RequestMethod.GET)
    public ExperimentDto getExperiment(@PathVariable("identifier") String identifier) {
        Experiment experiment = experimentService.getExperimentByIdentifier(identifier);
        return ExperimentDtoBuilder.fromExperiment(experiment);
    }

    @RequestMapping(value = "/api/experiment/{identifier}", method = RequestMethod.DELETE)
    public void removeExperiment(@PathVariable("identifier") String identifier) {
        Experiment experiment = experimentService.getExperimentByIdentifier(identifier);
        experimentService.removeExperiment(experiment);
    }

    @RequestMapping(value = "/api/experiment/{experimentId}/repository", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getRepository(@PathVariable("experimentId") String experimentId) throws IOException {
        Experiment experiment = experimentService.getExperimentByIdentifier(experimentId);
        StorageConfiguration storage = experiment.getConfiguration().getStorageConfiguration();
        Resource resource = storage.getStorageLocation();

        Path tempPath = applicationStorage.getTempDirectory();
        Path archive = tempPath.resolve(UUID.randomUUID().toString() + ".zip");

        ZipUtils.zip(resource.getFile(), archive.toFile());
        return new FileSystemResource(archive.toFile());
    }

    @RequestMapping(value = "/api/experiment/{experimentId}/repository", method = RequestMethod.POST)
    public void setRepository(@PathVariable String experimentId, @RequestParam MultipartFile repository) throws IOException {
        Experiment experiment = experimentService.getExperimentByIdentifier(experimentId);
        StorageConfiguration storage = experiment.getConfiguration().getStorageConfiguration();

        Path tempPath = applicationStorage.getTempDirectory();
        Path archive = tempPath.resolve(UUID.randomUUID().toString() + ".zip");

        try(InputStream inputStream = repository.getInputStream();
            OutputStream outputStream = new FileOutputStream(archive.toFile())) {
            IOUtils.copy(inputStream, outputStream);
        }
        Resource storageLocation = storage.getStorageLocation();
        ZipUtils.unzip(archive.toFile(), storageLocation.getFile());

        FileUtils.deleteQuietly(archive.toFile());
    }
}
