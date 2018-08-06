/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.sdk.publisher;

/**
 * Instances of this class publish data into an FTP server.
 *
 * @author Xiaolong Chen
 */
public class FTPConfig {
    private String hostName;
    private String userName;
    private String userPassword;
    private int port;

    public FTPConfig(String hostName, String userName, String userPassword, int port) {
        this.hostName = hostName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
