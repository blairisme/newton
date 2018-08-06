/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.system;

import org.ucl.newton.common.file.SystemPaths;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Instances of this class provide default application preferences, used
 * whenever a preference value is not supplied by the user.
 *
 * @author Blair Butterworth
 */
@Named
public class ApplicationPreferencesDefault
{
    @Inject
    public ApplicationPreferencesDefault() {
    }

    public String getDatabaseHost() {
        return "database-newton";
    }

    public String getDatabasePort() {
        return "3306";
    }

    public String getDatabaseUser() {
        return "root";
    }
    
    public String getDatabasePassword() {
        return "Newton*123";
    }

    public String getJupyterHost() {
        return "40.127.161.113";
    }
    
    public String getJupyterPort() {
        return "8000";
    }

    public String getProfile() {
        return "development";
    }

    public String getProgramDirectory() {
        return SystemPaths.getUserDirectory().resolve(".newton").toString();
    }

    public String getSlaveHost() {
        return "localhost";
    }

    public String getSlavePort() {
        return "8080";
    }

    public String getUploadDirectory() {
        return SystemPaths.getTempDirectory(".newton/uploads").toString();
    }
    
    public String getUploadMaximumSize() {
        return "5242880"; // 5 * 1024 * 1024;
    }
}
