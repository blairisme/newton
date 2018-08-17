package org.ucl.newton.drepublisher;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.ucl.newton.sdk.publisher.BasicFTPServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Instances of this class publish data into DRE.
 *
 * @author Xiaolong Chen
 */
public class DREFTPServer extends BasicFTPServer {

    public DREFTPServer(FTPConfig config){
        this.userName = config.getUserName();
        this.userPassword = config.getUserPassword();
        this.hostName = config.getHostName();
        this.port = config.getPort();
    }

    public void setFTPClient(FTPClient ftpClient){
        this.ftpClient = ftpClient;
    }
    
    private void initFTPClient() {
        setFTPClient(new FTPClient());
    }

    private boolean connect(){
        if(!ftpClient.isConnected()){
            try {
                ftpClient.connect(hostName,port);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else {
            return true;
        }
    }

    private boolean open() {
        if (!connect()){
            return false;
        }else {
            try {
                ftpClient.login(userName,userPassword);
                int reply = ftpClient.getReplyCode();
                if(!FTPReply.isPositiveCompletion(reply)){
                    close();
                    return false;
                }
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }

    }

    private void close() {
        if(ftpClient!=null){
            if(connect()){
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                    ftpClient = null;
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }

    public void upload(File file) {
        if(ftpClient==null){
            initFTPClient();
        }
        if (connect() && open()){
            try {
                InputStream input = new FileInputStream(file);
                ftpClient.storeFile(file.getName(),input);
                close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
