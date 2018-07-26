/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.system;

/**
 * Instances of this class contain application preferences, properties that
 * control the behaviour of the system.
 *
 * @author Blair Butterworth
 */
public interface ApplicationPreferences
{
    /**
     * The IP or host name of the Newton database.
     *
     * @return an IP or host name.
     */
    String getDatabaseHost();

    /**
     * The port number of the Newton database.
     *
     * @return a port number.
     */
    String getDatabasePort();

    /**
     * The user name to use when connecting to the Newton database.
     *
     * @return a database account name.
     */
    String getDatabaseUser();

    /**
     * The password (unencrypted) to use when connecting to the Newton
     * database.
     *
     * @return an unencrypted password.
     */
    String getDatabasePassword();

    /**
     * The IP or host name of a Jupyter hub instance which will be used to
     * allow users to edit the source code of experiments.
     *
     * @return an IP or host name.
     */
    String getJupyterHost();

    /**
     * The port number of a Jupyter hub instance which will be used to allow
     * users to edit the source code of experiments.
     *
     * @return a port number.
     */
    int getJupyterPort();

    /**
     * <p>
     * The profile of the Newton system. Used to control the introduction of
     * development only implementation, such as an in memory database instead
     * of a database service.
     * </p>
     * <p>
     *     Possible values include:
     *     <ul>
     *         <li>Development</li>
     *         <li>Production</li>
     *     </ul>
     * </p>
     *
     * @return a profile name.
     */
    String getProfile();

    /**
     * The file system location where Newton will store user content.
     *
     * @return a file system path.
     */
    String getProgramDirectory();

    /**
     * The IP or host name of the Newton slave instance or load balancer.
     *
     * @return an IP or host name.
     */
    String getSlaveHost();

    /**
     * The port number of the Newton slave instance or load balancer.
     *
     * @return a port number.
     */
    int getSlavePort();

    /**
     * The directory where user uploads will be stored before being persisted.
     *
     * @return a file system path.
     */
    String getUploadDirectory();

    /**
     * The maximum allowed size of user uploads, such as avatars.
     *
     * @return a maximum file size, specified in bytes.
     */
    int getUploadMaximumSize();
}
