package org.ucl.newton.service.data.plugin;



import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.ucl.newton.service.data.sdk.StorageProvider;



import java.io.OutputStream;
import java.util.Base64;
import java.util.Date;
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
//        String latitude ="51.524566";                       // required
//        String longitude="-0.134046";                       // required
//        String date = "2017-06-28";                         // can be null
//        String data = getDataFromAwhere(latitude, longitude, date);
        String city = "london";                             // required
        String country = "united kingdom";                  // can be null
        String date = "2018-07-03";                         // required and format yyyy-mm-dd

        String data = getDataFromWWO(city,country,date);

        displayOutput(data);
    }


    private String getDataFromWWO(String city,String country,String date) {
        String url = "https://api.worldweatheronline.com/premium/v1/past-weather.ashx";
        Map<String,String> header = new HashMap<>();
        Map<String,String> params = new HashMap<>();
        params.put("key","0252e94bd710446c908123539182906");
        params.put("format","json");

        String location = locationBuilder(city,country);    //location format q=city(,country)
        params.put("q",location);

        if (date !=null || date != "")
            params.put("date",date);
        String data = HttpUtils.doGet(url, header, params);
        return data;
    }

    private String locationBuilder(String city, String country) {
        city = city.replace(" ","+");
        if (country==null||country=="")
            return city;
        country = country.replace(" ", "+");
        return city + ","+country;
    }

    private void displayOutput(String data){
        try {
            OutputStream output = storageProvider.getOutputStream("weather");
            if(output!=null)
                output.write(data.getBytes("utf-8"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(data);
    }
    private String getDataFromAwhere(String latitude, String longitude,String date) {
        String token = getToken();

        String url = "https://api.awhere.com";

        url = url + "/v2/weather/locations/"+latitude + "," + longitude + "/observations";
        if( date != null || date != ""){
            url += "/"+ date;
        }

        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/json");
        header.put("Authorization","Bearer " + token);

        Map<String,String> params = new HashMap<>();
        String data = HttpUtils.doGet(url, header, params);
        return  data;
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

