/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.acceptance.newton.user;

import org.ucl.newton.common.network.RestResource;

/**
 * Defines various user service endpoints exposed by the Newton REST API.
 *
 * @author Blair Butterworth
 */
public enum UserResource implements RestResource
{
    User    ("user"),
    Users   ("users"),
    UpdateRole ("updaterole"),
    UserRole ("userrole");

    private String path;

    UserResource(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
