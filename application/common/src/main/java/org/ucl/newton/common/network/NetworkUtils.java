/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Instances of this class provide utility functions for query network
 * properties.
 *
 * @author Blair Butterworth
 */
public class NetworkUtils
{
    public static String getIPAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
