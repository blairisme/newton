/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.drepublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ucl.newton.common.serialization.CsvSerializer;
import org.ucl.newton.sdk.plugin.BasicConfiguration;
import org.ucl.newton.sdk.plugin.PluginHostContext;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Instances of this class publish data into an FTP server.
 *
 * @author Xiaolong Chen
 */
public class FTPConfig extends BasicConfiguration {
    private static Logger logger = LoggerFactory.getLogger(FTPConfig.class);
    private PluginHostContext context;
    private String hostName;
    private String userName;
    private String userPassword;
    private int port;

    public FTPConfig(String[] config){
        super("dre.html");
        this.hostName = config[0];
        this.userName = config[1];
        this.userPassword = config[2];
        this.port = Integer.parseInt(config[3]);
    }

    @Override
    public void setValues(Map<String, List<String>> values) {
        if (values.get("hostName")!=null)
            this.hostName = values.get("hostName").get(0);
        if (values.get("userName")!=null)
            this.userName = values.get("userName").get(0);
        if (values.get("userPassword")!=null)
            this.userPassword = values.get("userPassword").get(0);
        if (values.get("port")!=null)
            this.port = Integer.parseInt(values.get("port").get(0));
        writeToFile();
    }

    @Override
    public Map<String, String> getValues(){
        Map<String,String> values = new HashMap<>();
        values.put("hostName",hostName);
        values.put("userName",userName);
        values.put("userPassword",userPassword);
        values.put("port",Integer.toString(port));
        return values;

    }
    private void writeToFile() {
        try {
            OutputStream output = context.getStorage().getOutputStream("DREFTPConfiguration");
            CsvSerializer.writeCSV(output,buildList());
        }catch (IOException e){
            logger.error("Fail to write Fizzyo configuration");
        }
    }

    private List<List<String>> buildList() {
        List<List<String>> list = new ArrayList<>();
        list.add(buildHead());
        list.add(buildContent());
        return list;
    }

    private List<String> buildHead() {
        List<String> head = new ArrayList<>();
        head.add("hostName");
        head.add("userName");
        head.add("userPassword");
        head.add("port");
        return head;
    }

    private List<String> buildContent() {
        List<String> content = new ArrayList<>();
        content.add(hostName);
        content.add(userName);
        content.add(userPassword);
        content.add(Integer.toString(port));
        return content;
    }

    public String getHostName() {
        return hostName;
    }

    public String getUserName() {
        return userName;
    }


    public String getUserPassword() {
        return userPassword;
    }


    public int getPort() {
        return port;
    }

    public void setContext(PluginHostContext context) {
        this.context = context;
    }
}
