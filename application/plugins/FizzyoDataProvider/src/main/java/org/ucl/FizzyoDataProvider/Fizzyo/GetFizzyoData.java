package org.ucl.FizzyoDataProvider.Fizzyo;

import com.csvreader.CsvWriter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.util.Pair;
import org.ucl.FizzyoDataProvider.FileUtils;
import org.ucl.FizzyoDataProvider.Fizzyo.model.*;
import org.ucl.FizzyoDataProvider.HttpUtils;
import org.ucl.newton.service.data.sdk.StorageProvider;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Instances of this class provide utils for file reading.
 *
 * @author Xiaolong Chen
 */
public class GetFizzyoData implements Runnable {
    private StorageProvider storageProvider;
    private FizzyoToken fizzyoToken;
//    private String clientId = "00dc0898-8ff9-11e8-9eb6-529269fb1459";
//    private String sycSecret = "ml8rVoJX7axoJGggDo2xXJneyv4Ek36W7BErm0wMvbmMJOlKqAVzp0AbYAlO1nqV";
    public GetFizzyoData(StorageProvider storageProvider){
        this.storageProvider = storageProvider;
    }

    @Override
    public void run() {
//        fizzyoToken = new FizzyoToken();
//
//        String accessToken = "A1oRkpQJ0dNEGjwVLVmJKKzbLOvE2Mwz";
//        fizzyoToken.setAccessToken(accessToken);
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
        if(!listOfRecords.isEmpty())
            writeToOutput(listOfRecords);

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
        String authCode = FileUtils.readFile(path);
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


    private void writeToOutput(List<List<String>> listOfRecords) {
        try {
            OutputStream output = storageProvider.getOutputStream("FizzyoData");
            if (output != null) {
                CsvWriter csvWriter = new CsvWriter(output, ',', Charset.forName("utf-8"));
                for (List<String> record : listOfRecords) {
                    csvWriter.writeRecord(record.toArray(new String[record.size()]));
                }
                csvWriter.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }



}
