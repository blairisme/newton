/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.sdk.publisher;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;

/**
 * A basic FTP server implementation.
 *
 * @author Xiaolong Chen
 */
public abstract class BasicFTPServer {
    protected FTPClient ftpClient;
    protected String userName;
    protected String userPassword ;
    protected String hostName;
    protected int port;

    public abstract void upload(File file);

}
