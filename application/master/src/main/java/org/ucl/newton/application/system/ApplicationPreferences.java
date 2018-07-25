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

import javax.inject.Named;

/**
 * Instances of this class contain application preferences, system properties
 * that can be specified when running the system.
 *
 * @author Blair Butterworth
 */
@Named
public class ApplicationPreferences
{
    public String getDatabaseAddress() {
        return getProperty("newton.database.address", getDatabaseAddressDefault());
    }

    private String getDatabaseAddressDefault() {
        return "localhost";
    }

    public String getDatabasePort() {
        return getProperty("newton.database.port", getDatabasePortDefault());
    }

    private String getDatabasePortDefault() {
        return "3306";
    }

    public String getDatabaseUser() {
        return getProperty("newton.database.user", getDatabaseUserDefault());
    }

    private String getDatabaseUserDefault() {
        return "root";
    }

    public String getDatabasePassword() {
        return getProperty("newton.database.password", getDatabasePasswordDefault());
    }

    private String getDatabasePasswordDefault() {
        return "Newton*123";
    }

    public boolean getDatabasePopulate() {
        String value = getProperty("newton.database.populate", getDatabasePopulateDefault());
        return Boolean.valueOf(value);
    }

    private String getDatabasePopulateDefault() {
        return "false";
    }

    public String getProfile() {
        return getProperty("newton.profile", getProfileDefault());
    }

    private String getProfileDefault() {
        return "production";
    }

    public String getProgramDirectory() {
        return getProperty("newton.program.path", getProgramDirectoryDefault());
    }

    private String getProgramDirectoryDefault() {
        return SystemPaths.getUserDirectory().resolve(".newton").toString();
    }

    public String getUploadDirectory() {
        return getProperty("newton.upload.path", getUploadDirectoryDefault());
    }

    private String getUploadDirectoryDefault() {
        return SystemUtils.newTempDirectory(".newton/uploads").toString();
    }

    public int getUploadMaximumSize() {
        return Integer.parseInt(getProperty("newton.upload.max", getUploadMaximumSizeDefault()));
    }

    private String getUploadMaximumSizeDefault() {
        return "5242880"; // 5 * 1024 * 1024;
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
