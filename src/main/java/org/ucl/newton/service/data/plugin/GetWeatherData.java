package org.ucl.newton.service.data.plugin;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sun.misc.BASE64Encoder;


import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.util.HashMap;
import java.util.Map;


public class GetWeatherData implements Runnable {

    @Override
    public void run() {

        String token = getToken();
        String latitude ="51.524566";
        String longitude="-0.134046";
        String url = "https://api.awhere.com";
        url = url + "/v2/weather/locations/"+latitude + "," + longitude + "/observations";
        Map<String,String> header = new HashMap<String, String>();
        header.put("Content-Type","application/json");
        header.put("Authorization","Bearer " + token);
        Map<String,String> params = new HashMap<String, String>();

        String data = HttpUtils.doGet(url,params,header);

        System.out.println(data);
    }

    private String getToken(){
        String url = "https://api.awhere.com/oauth/token";
        String key = "QdF64BXLjzYMNvX2KHUgsQf8G0pGrBkp";
        String secret = "ecOFwYAdJ6Z8llGr";
        String credential = key + ":" + secret;

        BASE64Encoder encoder = new BASE64Encoder();
        String hashed = "";
        try {
            hashed = encoder.encode(credential.getBytes("utf-8"));
        }catch ( Exception e){
            e.printStackTrace();
        }


        Map<String,String> header = new HashMap<String, String>();
        header.put("Content-Type","application/x-www-form-urlencoded");
        header.put("Authorization",hashed);

        Map<String,String> params = new HashMap<String, String>();
        params.put("grant_type","client_credentials");

        String response = HttpUtils.doPost(url,params,header);
        JsonObject json = (JsonObject) new JsonParser().parse(response);
        String token = json.get("access_token").getAsString();
        return token;
    }
}

