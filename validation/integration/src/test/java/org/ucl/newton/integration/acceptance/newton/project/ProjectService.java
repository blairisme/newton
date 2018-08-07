package org.ucl.newton.integration.acceptance.newton.project;

import org.ucl.newton.common.network.RestServer;

public class ProjectService
{
    private RestServer server;

    public ProjectService(RestServer server) {
        this.server = server;
    }
}
