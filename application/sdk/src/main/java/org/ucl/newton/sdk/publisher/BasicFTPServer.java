package org.ucl.newton.sdk.publisher;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;

public abstract class BasicFTPServer {
    protected FTPClient ftpClient;
    protected String userName;
    protected String userPassword ;
    protected String hostName;
    protected int port;

    public abstract void upload(File file);

}
