package com.example.prototype;

import android.app.Application;
import com.onesignal.OneSignal;
public class ApplicationClass extends Application {
    private static final String ONESIGNAL_APP_ID = "cc2a2d25-3cb4-4dd8-b3cf-20194c1fc7b8";
    public void onCreate() {
        super.onCreate();

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}
