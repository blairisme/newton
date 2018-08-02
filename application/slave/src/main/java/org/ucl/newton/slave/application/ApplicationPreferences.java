/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.application;

import org.ucl.newton.common.file.SystemPaths;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.Path;

/**
 * Contains application preferences, properties that control the behaviour of
 * the system.
 *
 * @author Ziad Halabi
 * @author Blair Butterworth
 */
@Named
public class ApplicationPreferences
{
    @Inject
    public ApplicationPreferences(){
    }

    public String getMasterHost() {
        return getProperty("newton.master.host", "master-newton");
    }

    public int getMasterPort() {
        return Integer.parseInt(getProperty("newton.master.port", "9090"));
    }

    public String getApplicationHost() {
        return getProperty("newton.slave.host", "localhost");
    }

    public int getApplicationPort() {
        return Integer.parseInt(getProperty("newton.slave.port", "8080"));
    }

    public Path getApplicationPath() {
        return SystemPaths.getUserDirectory().resolve(".newton_slave");
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
