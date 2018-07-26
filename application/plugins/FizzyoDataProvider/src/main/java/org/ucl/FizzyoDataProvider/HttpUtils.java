package org.ucl.FizzyoDataProvider;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Instances of this class provide utils for http operations.
 *
 * @author Xiaolong Chen
 */
public class HttpUtils {
    static {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s){

                }
                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s){

                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            } };
            SSLContext sslcontext;
            sslcontext = SSLContext.getInstance("SSL");
            sslcontext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            Unirest.setHttpClient(httpclient);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
    public static String doPost(String url, Map header, Map params){

        try{
            HttpRequestWithBody request =Unirest.post(url).headers(header);
            request.fields(params);
//            request.body(toBody(params));
//            request.queryString(params);
            HttpResponse<String> response = request.asString();
            int status = response.getStatus();
            if (status >= 200 && status <300){
                return response.getBody();
            }
            else {
                System.out.println("status code:" + status);
                return response.getBody();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

//    private static String toBody(Map<String, String> params) {
//        List<String> paramList = new ArrayList<>();
//        for(String key : params.keySet()){
//            String value = params.get(key);
//            paramList.add(key + ": " + value);
//        }
//        return String.join("\\n",paramList);
//    }

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