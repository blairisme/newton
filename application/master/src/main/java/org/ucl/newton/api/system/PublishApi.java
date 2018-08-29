package org.ucl.newton.api.system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.ExperimentOutcome;
import org.ucl.newton.framework.ExperimentVersion;
import org.ucl.newton.sdk.publisher.DataPublisher;
import org.ucl.newton.service.experiment.ExperimentService;
import org.ucl.newton.service.plugin.PluginService;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Instances of this class expose publish service methods via REST.
 *
 * @author John Wilkie
 */
@RestController
@SuppressWarnings("unused")
public class PublishApi {

    private PluginService pluginService;
    private ExperimentService experimentService;

    @Inject
    public PublishApi(
            PluginService pluginService,
            ExperimentService experimentService)
    {
        this.pluginService = pluginService;
        this.experimentService = experimentService;
    }

    @RequestMapping(value = "/api/plugin/publish", method = RequestMethod.POST)
    public boolean publishData(
            @RequestParam(value="experiment") String experimentIdent,
            @RequestParam(value="version") String versionNum)
    {
        String publisherIdentifier = "newton-DRE";
        DataPublisher publisher = getPublisher(pluginService.getDataPublishers(), publisherIdentifier);
        if(publisher != null) {
            Experiment experiment = experimentService.getExperimentByIdentifier(experimentIdent);
            ExperimentVersion version = getVersion(experiment, versionNum);
            Collection<ExperimentOutcome> outcomes = version.getDataOutcomes();
            if (outcomes != null) {
               for(ExperimentOutcome outcome : outcomes){
                    publisher.start(outcome.getLocation());
                }
            }
            return true;
        }
        return false;
    }

    private ExperimentVersion getVersion(Experiment experiment, String version) {
        if (! experiment.hasVersion()) {
            return null;
        }
        if (version.equalsIgnoreCase("latest")) {
            return experiment.getLatestVersion();
        }
        return experiment.getVersion(Integer.parseInt(version));
    }

    private DataPublisher getPublisher(Collection<DataPublisher> publishers, String requiredIdentifier) {
        for(DataPublisher publisher :publishers) {
            if(publisher.getIdentifier().equals(requiredIdentifier)) {
                return publisher;
            }
        }
        return null;
    }
}