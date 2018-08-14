/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.acceptance.newton.project;

import org.ucl.newton.common.network.RestResource;

/**
 * Defines various project service endpoints exposed by the Newton REST API.
 *
 * @author Blair Butterworth
 */
public enum ProjectResource implements RestResource
{
    Project     ("project"),
    Projects    ("projects");

    private String path;

    ProjectResource(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }

    public String subPath(String subPath) {
        return path + "/" + subPath;
    }
}
