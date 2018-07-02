package org.ucl.newton.service.data.plugin;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Instances of this class provide weather data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class HttpUtils {

    public static String doPost(String url, Map header, String body){
        try{
            HttpRequestWithBody request =Unirest.post(url).headers(header);
            request.body(body);
            HttpResponse<String> response = request.asString();
            int status = response.getStatus();
            if (status >= 200 && status <300){
                return response.getBody();
            }
            else {
                System.out.println("status code:" + status);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String doGet(String url, Map header, Map params){
        try {
            GetRequest request =Unirest.get(url).headers(header);
            request.queryString(params);
            HttpResponse<String> response = request.asString();
            int status = response.getStatus();
            if (status >= 200 && status <300){
                return response.getBody();
            }
            else {
                System.out.println("status code:" + status);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
