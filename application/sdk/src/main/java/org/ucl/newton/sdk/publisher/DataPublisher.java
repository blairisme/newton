/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.sdk.publisher;

import org.ucl.newton.sdk.plugin.NewtonPlugin;

/**
 * Instances of this class publish data into a external service, which may be
 * local or remote and where data may be stored permanently or temporarily.
 *
 * @author Xiaolong Chen
 */
public interface DataPublisher extends NewtonPlugin {
    void start(String filePath);
    String getConfigName();
    Class<?> getConfigClass();
    <T> void setConfig(T config);
}
