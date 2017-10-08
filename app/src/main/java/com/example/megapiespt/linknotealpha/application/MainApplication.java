package com.example.megapiespt.linknotealpha.application;

import android.app.Application;

import com.example.megapiespt.linknotealpha.util.Contextor;
import com.example.megapiespt.linknotealpha.util.LogWrapper;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by MegapiesPT on 15/6/2560.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogWrapper.d("Application onCreate");
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LogWrapper.d("Application onTerminate");
    }
}
