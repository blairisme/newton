/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.system;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Instances of this class provide application preferences, either specified by
 * the user or a suitable default.
 *
 * @author Blair Butterworth
 */
@Named
public class ApplicationPreferencesUser implements ApplicationPreferences
{
    private ApplicationPreferencesDefault defaults;

    @Inject
    public ApplicationPreferencesUser(ApplicationPreferencesDefault defaults) {
        this.defaults = defaults;
    }

    @Override
    public String getDatabaseHost() {
        return getProperty("newton.database.host", defaults.getDatabaseHost());
    }

    @Override
    public String getDatabasePort() {
        return getProperty("newton.database.port", defaults.getDatabasePort());
    }

    @Override
    public String getDatabaseUser() {
        return getProperty("newton.database.user", defaults.getDatabaseUser());
    }

    @Override
    public String getDatabasePassword() {
        return getProperty("newton.database.password", defaults.getDatabasePassword());
    }

    @Override
    public String getJupyterHost() {
        return getProperty("newton.jupyter.host", defaults.getJupyterHost());
    }

    @Override
    public int getJupyterPort() {
        return Integer.parseInt(getProperty("newton.jupyter.port", defaults.getJupyterPort()));
    }

    @Override
    public String getProfile() {
        return getProperty("newton.profile", defaults.getProfile());
    }

    @Override
    public String getProgramDirectory() {
        return getProperty("newton.program.path", defaults.getProgramDirectory());
    }

    @Override
    public String getSlaveHost() {
        return getProperty("newton.slave.host", defaults.getSlaveHost());
    }

    @Override
    public int getSlavePort() {
        return Integer.parseInt(getProperty("newton.slave.port", defaults.getSlavePort()));
    }

    @Override
    public String getUploadDirectory() {
        return getProperty("newton.upload.path", defaults.getUploadDirectory());
    }

    @Override
    public int getUploadMaximumSize() {
        return Integer.parseInt(getProperty("newton.upload.max", defaults.getUploadMaximumSize()));
    }

    private String getProperty(String name, String defaultValue) {
        String result = System.getProperty(name);
        if (result != null) {
            return result;
        }
        result = System.getenv(name);
        if (result != null) {
            return result;
        }
        result = System.getenv(name.replace(".", "_"));
        if (result != null) {
            return result;
        }
        return defaultValue;
    }
}
