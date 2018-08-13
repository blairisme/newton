/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.fizzyo;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.common.network.HttpUtils;
import org.ucl.newton.fizzyo.model.*;
import org.ucl.newton.sdk.provider.DataSource;
import org.ucl.newton.sdk.provider.DataStorage;

import java.io.IOException;
import java.io.OutputStream;

import java.util.*;

/**
 * Instances of this class provide data from Fizzyo.
 *
 * @author Xiaolong Chen
 */
public class GetFizzyoData implements Runnable
{
    private static Logger logger = LoggerFactory.getLogger(GetFizzyoData.class);

    private FizzyoDataProvider provider;

    public GetFizzyoData(FizzyoDataProvider provider){
        this.provider = provider;
    }

    @Override
    public void run() {

        SyncData syncData = getFizzyoSyncData();
        if (syncData == null)
            return;
        syncData.getData().initMapper();
        FizzyoData fizzyoData = syncData.getData();

        for(String name : syncData.getRequestedData()){

            List<List<String>> listOfRecords = new ArrayList<>();
            listOfRecords.add(fizzyoData.getKeys(name));
            listOfRecords.addAll(fizzyoData.getValues(name));

            if(listOfRecords.size()>1) {
                writeToOutput(listOfRecords,name);
                dataUpdated(name);
            }

        }
    }

    private SyncData getFizzyoSyncData() {

        String clientId = "00000000-0000-0000-0000-000000000001";
        String syncSecret = "A1oRkpQJ0dNX1z3RA2K2zKKaLOvE2MwzA1oRkpQJ0dNxxDoJNonZWDzaLOvE2Mwz";
        String startData = "1470744743";
        String endDate = "1533816743";
        String requestData = "[ \"all\" ]";

        String url = "https://api-staging.fizzyo-ucl.co.uk/api/v1/sync-data/interval";
        Map<String,String> header = new HashMap<>();
        Map<String,String> params = new HashMap<>();
        header.put("Authorization","Bearer "+ clientId +","+ syncSecret);
        params.put("clientId",clientId);
        params.put("startDate",startData);
        params.put("endDate", endDate);
        params.put("requestedData",requestData);

        String data = HttpUtils.doGet(url,header,params);
        Gson gson = new Gson();
        FizzyoResponse response = gson.fromJson(data,FizzyoResponse.class);
        return response.getSyncData();
    }


    private void dataUpdated(String name) {
        for (DataSource dataSource: provider.getDataSources()){
            if (dataSource.getName().equals(name))
                provider.notifyDataUpdated(dataSource);
        }
    }
    private void writeToOutput(List<List<String>> list,String name) {
        DataStorage storage = provider.getStorage();
        for(DataSource dataSource : provider.getDataSources()){
            if (dataSource.getName().equals(name)) {
                try (OutputStream output = storage.getOutputStream(dataSource)) {
                    FileUtils.writeCSV(output, list);
                } catch (IOException e) {
                    logger.error("Failed to write Fizzyo data",e);
                }
            }
        }
    }
}
