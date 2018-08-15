/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.network;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * Instances of this class provide utils for http operations.
 *
 * @author Xiaolong Chen
 */
@SuppressWarnings("unchecked")
public class HttpUtils
{
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private HttpUtils() {
        throw new UnsupportedOperationException();
    }

    static {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s){
                    // Trust all certificates
                }
                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s){
                    // Trust all servers
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
        }
        catch (Exception e) {
            logger.error("Failed to configure certificate store", e);
        }
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
                logger.info("status code:" + status);
            }
        }catch (Exception e){
            logger.error("Failed to get", e);
        }
        return null;
    }
}
