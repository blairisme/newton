/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.acceptance.newton.experiment;

import com.google.common.collect.ImmutableMap;
import org.ucl.newton.common.network.RestException;
import org.ucl.newton.common.network.RestRequest;
import org.ucl.newton.common.network.RestResponse;
import org.ucl.newton.common.network.RestServer;

import java.util.Collection;

/**
 * Provides access to experiment services the Newton REST API.
 *
 * @author Blair Butterworth
 */
public class ExperimentService
{
    private RestServer server;

    public ExperimentService(RestServer server) {
        this.server = server;
    }

    public void addExperiment(ExperimentDto experiment) throws RestException {
        RestRequest request = server.post(ExperimentResource.Experiment);
        request.setBody(experiment, ExperimentDto.class);
        request.make();
    }

    public void addExperiments(Collection<ExperimentDto> experiments) throws RestException {
        for (ExperimentDto experiment: experiments) {
            addExperiment(experiment);
        }
    }

    public Collection<ExperimentDto> getExperiments(String project) throws RestException {
        RestRequest request = server.get(ExperimentResource.Experiments);
        request.setParameters(ImmutableMap.of("project", project));

        RestResponse response = request.make();
        ExperimentDtoSet experimentSet = response.asType(ExperimentDtoSet.class);

        return experimentSet.getExperiments();
    }

    public void removeExperiment(ExperimentDto experiment) throws RestException {
        RestRequest request = server.delete(ExperimentResource.Experiment.subPath(experiment.getIdentifier()));
        request.make();
    }

    public void removeExperiments(Collection<ExperimentDto> experiments) throws RestException {
        for (ExperimentDto experiment: experiments) {
            removeExperiment(experiment);
        }
    }
}
