package org.ucl.DREDataPublisher.model;


public class FTPConfig{
    String hostName;
    String userName;
    String userPassword;
    int port;

    public FTPConfig(String hostName, String userName, String userPassword, int port) {
        this.hostName = hostName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.port = port;
    }

    public String getHostName() { return hostName; }
    public void setHostName(String hostName) { this.hostName = hostName; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }
    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }
}
