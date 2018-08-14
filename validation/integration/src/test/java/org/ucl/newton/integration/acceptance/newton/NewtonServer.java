/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.acceptance.newton;

import org.apache.http.HttpHeaders;
import org.ucl.newton.common.network.AuthenticatedRestServer;
import org.ucl.newton.common.network.BasicAuthentication;
import org.ucl.newton.common.network.MimeTypes;
import org.ucl.newton.common.network.RestServer;
import org.ucl.newton.common.serialization.JsonSerializer;
import org.ucl.newton.integration.acceptance.newton.experiment.ExperimentService;
import org.ucl.newton.integration.acceptance.newton.project.ProjectService;
import org.ucl.newton.integration.acceptance.newton.user.UserService;

/**
 * Provides access to Newton server functionality via a thin Java wrapper
 * around the Newton REST API.
 *
 * @author Blair Butterworth
 */
public class NewtonServer
{
    private UserService userService;
    private ProjectService projectService;
    private ExperimentService experimentService;

    public NewtonServer() {
        RestServer restServer = getRestServer();
        userService = new UserService(restServer);
        projectService = new ProjectService(restServer);
        experimentService = new ExperimentService(restServer);
    }

    public UserService getUserService() {
        return userService;
    }

    public ProjectService getProjectService() {
        return projectService;
    }

    public ExperimentService getExperimentService() {
        return experimentService;
    }

    private RestServer getRestServer() {
        AuthenticatedRestServer restServer = new AuthenticatedRestServer();
        restServer.setAddress("http://localhost:9090/api/");
        restServer.setSerializer(new JsonSerializer());
        restServer.addHeader(HttpHeaders.CONTENT_TYPE, MimeTypes.JSON);
        restServer.addHeader(HttpHeaders.ACCEPT, MimeTypes.JSON);
        restServer.setAuthenticationStrategy(new BasicAuthentication("api@newton.com", "password"));
        return restServer;
    }
}
