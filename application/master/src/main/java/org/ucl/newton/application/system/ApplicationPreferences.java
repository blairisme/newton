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
    public String getDatabaseUser() {
        return System.getProperty("newton.database.user", getDatabaseUserDefault());
    }

    private String getDatabaseUserDefault() {
        return "root";
    }

    public String getDatabasePassword() {
        return System.getProperty("newton.database.password", getDatabasePasswordDefault());
    }

    private String getDatabasePasswordDefault() {
        return "password";
    }

    public boolean getDatabasePopulate() {
        String value = System.getProperty("newton.database.populate", getDatabasePopulateDefault());
        return Boolean.valueOf(value);
    }

    private String getDatabasePopulateDefault() {
        return "false";
    }

    public String getProfile() {
        return System.getProperty("newton.profile", getProfileDefault());
    }

    private String getProfileDefault() {
        return "production";
    }

    public String getProgramDirectory() {
        return System.getProperty("newton.program.path", getProgramDirectoryDefault());
    }

    private String getProgramDirectoryDefault() {
        return SystemPaths.getUserDirectory().resolve(".newton").toString();
    }

    public String getUploadDirectory() {
        return System.getProperty("newton.upload.path", getUploadDirectoryDefault());
    }

    private String getUploadDirectoryDefault() {
        return SystemUtils.newTempDirectory(".newton/uploads").toString();
    }

    public int getUploadMaximumSize() {
        String property = System.getProperty("newton.upload.max");
        return property != null ? Integer.parseInt(property) : getUploadMaximumSizeDefault();
    }

    private int getUploadMaximumSizeDefault() {
        return 5 * 1024 * 1024;
    }
}
