package org.ucl.WeatherDataProvider;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;

import java.util.Map;
/**
 * Instances of this class provide utils for http operations.
 *
 * @author Xiaolong Chen
 */
public class HttpUtils {

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
