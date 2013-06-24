package com.gregttn.GCMClientDemoAndroidApp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.provider.Settings.Secure;

import com.google.android.gcm.GCMBaseIntentService;
import com.gregttn.GCMClientDemoAndroidApp.model.NotificationInfo;
import com.gregttn.GCMClientDemoAndroidApp.utils.NotificationDispatcher;
import com.gregttn.GCMClientDemoAndroidApp.utils.ServerGateway;


public class GCMIntentService extends GCMBaseIntentService {
    private static final String TAG = GCMIntentService.class.getName();
    private static final String MESSAGE_KEY = "message";
    public static final String SERVER_REGISTER_ENDPOINT = "YOUR SERVER ENDPOINT";

    private ServerGateway serverGateway = new ServerGateway(SERVER_REGISTER_ENDPOINT);
    private NotificationDispatcher notificationDispatcher;

    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "onMessage");

        String message = intent.getExtras().getString(MESSAGE_KEY);
        NotificationInfo notificationInfo = new NotificationInfo(message);
        getNotificationDispatcher().dispatchNotification(notificationInfo);
    }

    @Override
    protected void onError(Context context, String s) {
        Log.wtf(TAG, "onError");
    }

    @Override
    protected void onRegistered(Context context, String regId) {
        Log.i(TAG, "onRegister " + regId);

        registerDeviceBackground(regId);
    }

    @Override
    protected void onUnregistered(Context context, String s) {
        Log.i(TAG, "onUnregistered");
    }

    private void registerDeviceBackground(final String regId) {
        new AsyncTask<Object, Object, Boolean>() {
            @Override
            protected Boolean doInBackground(Object... params) {
                String deviceId = Secure.getString(getApplicationContext().getContentResolver(),Secure.ANDROID_ID);
                return serverGateway.registerDevice(deviceId, regId);
            }

            @Override
            protected void  onPostExecute(Boolean result) {
                if(!result) {
                    NotificationInfo errorNotification = new NotificationInfo("Error", "Could not register device");
                    getNotificationDispatcher().dispatchNotification(errorNotification);
                }
            }
        }.execute();
    }

    private NotificationDispatcher getNotificationDispatcher() {
        if (notificationDispatcher == null) {
            notificationDispatcher = new NotificationDispatcher(getApplicationContext());
        }

        return notificationDispatcher;
    }
}
