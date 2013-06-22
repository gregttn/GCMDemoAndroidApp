package com.gregttn.GCMClientDemoAndroidApp.utils;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ServerGateway {
    private final String DEVICE_ID_KEY = "deviceId";
    private final String REG_ID_KEY = "registrationId";

    private String serverAddress;
    private HttpClient client;

    public ServerGateway(String serverAddress) {
        this.serverAddress = serverAddress;
        this.client = new DefaultHttpClient();
    }

    public boolean registerDevice(String deviceId, String regId) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(DEVICE_ID_KEY, deviceId));
        params.add(new BasicNameValuePair(REG_ID_KEY, regId));

        HttpPost request = new HttpPost(serverAddress);

        try {
            request.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return false;
            }
        } catch (Exception e) {
            Log.w(getClass().getName(), e);

            return false;
        }

        return true;
    }
}
