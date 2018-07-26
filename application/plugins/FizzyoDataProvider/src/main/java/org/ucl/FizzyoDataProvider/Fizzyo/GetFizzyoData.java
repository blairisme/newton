package org.ucl.FizzyoDataProvider.Fizzyo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.ucl.FizzyoDataProvider.Fizzyo.model.*;
import org.ucl.FizzyoDataProvider.HttpUtils;
import org.ucl.newton.service.data.sdk.StorageProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Instances of this class provide utils for file reading.
 *
 * @author Xiaolong Chen
 */
public class GetFizzyoData implements Runnable {
    private StorageProvider storageProvider;
    private FizzyoToken fizzyoToken;
    private String clientId = "00dc0898-8ff9-11e8-9eb6-529269fb1459";
    private String sycSecret = "ml8rVoJX7axoJGggDo2xXJneyv4Ek36W7BErm0wMvbmMJOlKqAVzp0AbYAlO1nqV";
    public GetFizzyoData(StorageProvider storageProvider){
        this.storageProvider = storageProvider;
    }

    @Override
    public void run() {

//        fizzyoToken = new FizzyoToken();
//
//        String accessToken = "NR8M7Vl4zbqoy0mN8zkvGpeprOZqEgL3";
//        fizzyoToken.setAccessToken(accessToken);

        Records records = getPacientRecords();
        System.out.println("records size: " + records.getRecords().size());
        for(PacientRecord record : records.getRecords()){
            String patientRecordId = record.getId();
            System.out.println("PatientRecordId: "+patientRecordId);
            Pressures pressures = getPressures(patientRecordId);
            System.out.println("Pressures size: " + pressures.getPressure().size());
            for (PressureRecord pressureRecord : pressures.getPressure()){
                String pressureRawId = pressureRecord.getPressureRawId();
                PressureRaw pressureRaw = getPressureRaw(pressureRawId);
                System.out.println("Raw size: " + pressureRaw.getPressure().getPressureValues().size());
            }
        }


//        params.put("clientId", clientId);
//        String data = HttpUtils.doGet(url,header,params);
//        System.out.println(data);

//        header.put("Conteng-type","application/x-www-form-urlencoded");


//        header.put("Authorization", "Bearer yNzqmQO5xe7WY6OGjKDXKwre7rJX0A4o");


//
//        params.put("clientId", "UCLNewton");
//        params.put("startDate", "2018-03-31");
//        params.put("endDate ", "2018-04-01");
//        params.put("requestedData", "[\"pressure-raw\", \"heart-rate\", \"games-sessions\"]");
//        params.put("authCode","M02ebf066-fe91-8712-0f98-09eaefcb4779");



//        String authCode = getAuthCode();
//        System.out.println(authCode);
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
//        System.out.println(data);
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
//        System.out.println(data);
        Gson gson = new Gson();
        Records records = gson.fromJson(data,Records.class);
        return records;

    }

    private FizzyoToken getFizzyoToken() {
        if(fizzyoToken!=null)
            return fizzyoToken;
        String url = "https://api-staging.fizzyo-ucl.co.uk/api/v1/auth/token";
        Map<String,String> header = new HashMap<>();
        Map<String,String> params = new HashMap<>();
        params.put("redirectUri","https://staging.fizzyo-ucl.co.uk/login");
        params.put("authCode",getAuthCode());  //this should get from configuration
        String data = HttpUtils.doPost(url, header, params);
        if(data == null)
            return null;
        System.out.println(data);
        Gson gson = new Gson();
        fizzyoToken = gson.fromJson(data,FizzyoToken.class);

        return fizzyoToken;

    }

    private String getAuthCode() {
        return "Mbb23413c-f627-240b-e752-53a70a48c4d8";
    }

//    private String getAuthCode() {
//        String authCode = null;
//        String url = "https://login.live.com/oauth20_authorize.srf";
//        Map<String, String> header = new HashMap<>();
//        Map<String, String> params = new HashMap<>();
//
//        params.put("client_id","65973b85-c34f-41a8-a4ad-00529d1fc23c");
//        params.put("redirect_uri","https://staging.fizzyo-ucl.co.uk/login");
//        params.put("response_type","code");
//        params.put("scope", "wl.basic wl.offline_access wl.signin wl.phone_numbers wl.emails");
//
//        String data = HttpUtils.doGet(url,header,params);
//        return data;
//    }

}
