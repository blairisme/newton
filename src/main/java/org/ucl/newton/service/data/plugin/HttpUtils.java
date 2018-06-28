package org.ucl.newton.service.data.plugin;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


import java.net.URI;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

public class HttpUtils {
    public static String doPost(String url, Map<String,String> params, Map<String,String> header){

        try {
            HttpClient client= new DefaultHttpClient();
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));

            for (String key :header.keySet()){
                request.setHeader(key,header.get(key));
            }

            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            for (String key :params.keySet()){
                pairs.add(new BasicNameValuePair(key, params.get(key)));
            }

            request.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));

            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode <300){
                HttpEntity entity = response.getEntity();
                String result = null;
                if (entity != null) {
                    result = EntityUtils.toString(entity, "utf-8");
                }
                return result;
            }
            else{
                System.out.println("status code：" + statusCode);
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String doGet(String url, Map<String,String> params, Map<String, String> header){

        try {
            HttpClient client= new DefaultHttpClient();
            HttpGet request = new HttpGet();

            for (String key :header.keySet()){
                request.setHeader(key,header.get(key));
            }
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            for (String key :params.keySet()){
                pairs.add(new BasicNameValuePair(key, params.get(key)));
            }
            if (!pairs.isEmpty()){
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs), "utf-8");
            }
            request.setURI(new URI(url));

            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode <300){
                HttpEntity entity = response.getEntity();
                String result = null;
                if (entity != null) {
                    result = EntityUtils.toString(entity, "utf-8");
                }
                return result;
            }
            else{
                System.out.println("status code：" + statusCode);
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
