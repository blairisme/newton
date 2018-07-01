package org.ucl.newton.service.data.plugin;



import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.ucl.newton.service.data.sdk.StorageProvider;



import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Instances of this class provide weather data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class GetWeatherData implements Runnable{
    private StorageProvider storageProvider;
    public GetWeatherData(StorageProvider storageProvider){
        this.storageProvider = storageProvider;
    }
    @Override
    public void run() {

        String token = getToken();

        String latitude ="51.524566";
        String longitude="-0.134046";
        String url = "https://api.awhere.com";
        String day = "28";
        String month = "06";
        String year = "2017";
        String date = year+"-"+month+"-"+day;
        url = url + "/v2/weather/locations/"+latitude + "," + longitude + "/observations"; //" + date+"?properties=temperatures,precipitation" ;

        System.out.println(url);
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/json");
        header.put("Authorization","Bearer " + token);

        Map<String,String> params = new HashMap<>();

        String data = HttpUtils.doGet(url,header,params);
        try {
            OutputStream output = storageProvider.getOutputStream("weather");
            if(output!=null)
                output.write(data.getBytes("utf-8"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(data);
    }

    private String getToken(){
        String url = "https://api.awhere.com/oauth/token";
        String hashed = getHashedCredential();

        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/x-www-form-urlencoded");
        header.put("Authorization",hashed);

        String body = "grant_type=client_credentials";

        String response = HttpUtils.doPost(url,header,body);

        JsonObject json = (JsonObject) new JsonParser().parse(response);
        String token = json.get("access_token").getAsString();

        return token;
    }
    private String getHashedCredential(){
        String key = "QdF64BXLjzYMNvX2KHUgsQf8G0pGrBkp";
        String secret = "ecOFwYAdJ6Z8llGr";
        String credential = key + ":" + secret;

        String hashed = "";
        try {
            hashed = Base64.getEncoder().encodeToString(credential.getBytes("utf-8"));
        }catch ( Exception e){
            e.printStackTrace();
        }

        return hashed;
    }
}

