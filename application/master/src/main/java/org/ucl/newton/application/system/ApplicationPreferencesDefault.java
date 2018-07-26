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
import org.ucl.newton.common.file.SystemUtils;

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

    public String getDatabaseAddress() {
        return "localhost";
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

    public String getJupyterAddress() {
        return "localhost";
    }
    
    public String getJupyterPort() {
        return "8000";
    }

    public String getProfile() {
        return "production";
    }

    public String getProgramDirectory() {
        return SystemPaths.getUserDirectory().resolve(".newton").toString();
    }
    
    public String getUploadDirectory() {
        return SystemUtils.newTempDirectory(".newton/uploads").toString();
    }
    
    public String getUploadMaximumSize() {
        return "5242880"; // 5 * 1024 * 1024;
    }
}
