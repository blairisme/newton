package org.ucl.newton.fizzyo;

import com.google.gson.Gson;
import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.common.network.HttpUtils;
import org.ucl.newton.fizzyo.model.*;
import org.ucl.newton.sdk.data.DataSource;
import org.ucl.newton.sdk.data.DataStorage;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Instances of this class provide data from Fizzyo.
 *
 * @author Xiaolong Chen
 */
public class GetFizzyoData implements Runnable {
    private FizzyoToken fizzyoToken;
    private FizzyoDataProvider provider;

//    private String clientId = "00dc0898-8ff9-11e8-9eb6-529269fb1459";
//    private String sycSecret = "ml8rVoJX7axoJGggDo2xXJneyv4Ek36W7BErm0wMvbmMJOlKqAVzp0AbYAlO1nqV";
    public GetFizzyoData(FizzyoDataProvider provider){
        this.provider = provider;
    }

    @Override
    public void run() {
        fizzyoToken = new FizzyoToken();
        String accessToken = "9w3Bk61YQdJ6VrXR5vJN592dOGpJZn4W";
        fizzyoToken.setAccessToken(accessToken);
        Records records = getPacientRecords();
        if(records == null)
            return;
        List<List<String>> listOfRecords = new ArrayList<>();
        listOfRecords.add(getHeader());
        for(PacientRecord record : records.getRecords()){
            String patientRecordId = record.getId();
            Pressures pressures = getPressures(patientRecordId);

            for (PressureRecord pressureRecord : pressures.getPressure()){
                String pressureRawId = pressureRecord.getPressureRawId();
                PressureRaw pressureRaw = getPressureRaw(pressureRawId);
                listOfRecords.add(getContent(pressureRecord,pressureRaw.getPressure()));
            }
        }
        if(!listOfRecords.isEmpty()) {
            writeToOutput(listOfRecords);
            provider.notifyDataUpdated(provider.getFizzyoDataSource());
        }
    }

    private PressureRaw getPressureRaw(String pressureRawId) {
        String url = "https://api-staging.fizzyo-ucl.co.uk/api/v1/pressure/" + pressureRawId + "/raw";
        Map<String,String> header = new HashMap<>();
        Map<String,String> params = new HashMap<>();
        header.put("Authorization", "Bearer "+ getFizzyoToken().getAccessToken());
        String data = HttpUtils.doGet(url,header,params);
        Gson gson = new Gson();
        PressureRaw pressureRaw = gson.fromJson(data,PressureRaw.class);
        return pressureRaw;
    }

    private Pressures getPressures(String patientRecordId) {
        String url = "https://api-staging.fizzyo-ucl.co.uk/api/v1/pressure/" + patientRecordId;
        Map<String,String> header = new HashMap<>();
        Map<String,String> params = new HashMap<>();
        header.put("Authorization", "Bearer "+ getFizzyoToken().getAccessToken());
        String data = HttpUtils.doGet(url,header,params);
        Gson gson = new Gson();
        Pressures pressures = gson.fromJson(data,Pressures.class);
        return pressures;

    }

    private Records getPacientRecords() {
        String url = "https://api-staging.fizzyo-ucl.co.uk/api/v1/patient-records";
        Map<String,String> header = new HashMap<>();
        Map<String,String> params = new HashMap<>();
        header.put("Authorization","Bearer "+ getFizzyoToken().getAccessToken());
        String data = HttpUtils.doGet(url,header,params);
        Gson gson = new Gson();
        Records records = gson.fromJson(data,Records.class);
        return records;
    }

    private FizzyoToken getFizzyoToken() {
        if(fizzyoToken!=null)
            return fizzyoToken;
        String authCode = getAuthCode();
        if(authCode==null) {
            FizzyoToken fizzyoToken = new FizzyoToken();
            fizzyoToken.setAccessToken("i don't know");
            return fizzyoToken;
        }
        String url = "https://api-staging.fizzyo-ucl.co.uk/api/v1/auth/token";
        Map<String,String> header = new HashMap<>();
        Map<String,String> params = new HashMap<>();
        params.put("redirectUri","https://staging.fizzyo-ucl.co.uk/login");
        params.put("authCode",authCode);
        String data = HttpUtils.doPost(url, header, params);
        if(data == null)
            return null;
        System.out.println(data);
        Gson gson = new Gson();
        fizzyoToken = gson.fromJson(data,FizzyoToken.class);
        return fizzyoToken;
    }

    private String getAuthCode() {
        Path path = Paths.get(System.getProperty("user.home")).resolve(".newton");
        path = path.resolve("Fizzyo").resolve("authCode");
        String authCode = FileUtils.readFile(path.toFile());
        return authCode;
    }

    private List<String> getHeader() {
        List<String> header = new ArrayList<>();
        header.addAll(new PressureRecord().getKeys());
        header.addAll(new PressureRawRecord().getKeys());

        return header;
    }

    private List<String> getContent(PressureRecord pressureRecord, PressureRawRecord pressureRawRecord) {
        List<String> content = new ArrayList<>();
        content.addAll(pressureRecord.getValues());
        content.addAll(pressureRawRecord.getValues());
        return content;
    }

    private void writeToOutput(List<List<String>> list) {
        DataStorage storage = provider.getStorage();
        DataSource dataSource = provider.getFizzyoDataSource();
        try (OutputStream output = storage.getOutputStream(dataSource)){
            FileUtils.writeCSV(output, list);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
