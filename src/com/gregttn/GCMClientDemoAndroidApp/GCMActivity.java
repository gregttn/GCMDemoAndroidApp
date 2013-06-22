package com.gregttn.GCMClientDemoAndroidApp;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.gcm.GCMRegistrar;

public class GCMActivity extends Activity {

    public static final String PROJECT_ID = "YOUR PROJECT ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);

        String regId = GCMRegistrar.getRegistrationId(this);

        if ("".equals(regId)) {
            GCMRegistrar.register(this, PROJECT_ID);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        GCMRegistrar.unregister(this);
    }
}
