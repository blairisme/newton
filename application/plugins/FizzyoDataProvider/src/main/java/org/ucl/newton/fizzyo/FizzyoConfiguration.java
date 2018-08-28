package org.ucl.newton.fizzyo;

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
 * Instances of this class provide Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class FizzyoConfiguration extends BasicConfiguration {
    private static Logger logger = LoggerFactory.getLogger(FizzyoConfiguration.class);
    private PluginHostContext context;
    private String clientId;
    private String syncSecret;
    private String startDate;
    private String endDate;
    private String requestData;

    public FizzyoConfiguration() {
        super("fizzyo.html");
    }

    public void setValues(String[] configuration) {
        this.clientId = configuration[0];
        this.syncSecret = configuration[1];
        this.startDate = configuration[2];
        this.endDate = configuration[3];
        this.requestData = configuration[4];
    }

    @Override
    public void setValues(Map<String, List<String>> values){
        if (values.get("clientId")!=null)
            this.clientId = values.get("clientId").get(0);
        if (values.get("syncSecret")!=null)
          this.syncSecret = values.get("syncSecret").get(0);
        if (values.get("startDate")!=null) {
            String timeStr = values.get("startDate").get(0) + " 00:00:00";
            this.startDate = TimeUtils.timeToUnixStamp(timeStr);
        }
        if (values.get("endDate")!=null) {
            String timeStr = values.get("endDate").get(0) + " 00:00:00";
            this.endDate = TimeUtils.timeToUnixStamp(timeStr);
        }
        if (values.get("requestData")!=null)
            this.requestData = values.get("requestData").get(0);
        writeToFile();
    }

    @Override
    public Map<String, String> getValues(){
        Map<String, String> values = new HashMap<>();
        if (clientId != null) {
            values.put("clientId", clientId);
        }
        if (syncSecret != null) {
            values.put("syncSecret", syncSecret);
        }
        if (startDate != null) {
            values.put("startDate", TimeUtils.unixStampToTime(startDate).substring(0, 10));
        }
        if (endDate != null) {
            values.put("endDate", TimeUtils.unixStampToTime(endDate).substring(0, 10));
        }
        if (requestData != null) {
            values.put("requestData", requestData);
        }
        return values;
    }

    private void writeToFile() {
        try {
            OutputStream output = context.getStorage().getOutputStream("FizzyoConfiguration");
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
        head.add("clientId");
        head.add("syncSecret");
        head.add("startDate");
        head.add("endDate");
        head.add("requestData");
        return head;
    }

    private List<String> buildContent() {
        List<String> content = new ArrayList<>();
        content.add(clientId);
        content.add(syncSecret);
        content.add(startDate);
        content.add(endDate);
        content.add(requestData);
        return content;
    }

    public void setContext(PluginHostContext context){
        this.context = context;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSyncSecret() {
        return syncSecret;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getRequestData() {
        return requestData;
    }
}
