/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.webapp;

import org.ucl.newton.common.SystemPaths;
import org.ucl.newton.common.SystemUtils;

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
