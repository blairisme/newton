/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.sdk.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Instances of this interface provide access to application persistence.
 *
 * @author Blair Butterworth
 * @author Xiaolong Chen
 */
public interface PluginHostStorage
{
    InputStream getInputStream(String id) throws IOException;

    OutputStream getOutputStream(String id) throws IOException;
}
