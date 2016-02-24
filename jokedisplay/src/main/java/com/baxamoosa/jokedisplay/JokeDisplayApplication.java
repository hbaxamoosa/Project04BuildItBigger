package com.baxamoosa.jokedisplay;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import timber.log.Timber;

public class JokeDisplayApplication extends Application {

    private static Context context;

    public static Context getAppContext() {
        return JokeDisplayApplication.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        JokeDisplayApplication.context = getApplicationContext();

        //Including Jake Wharton's Timber logging library
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Timber.v("Timber.plant(new Timber.DebugTree());");
        } else {
            // Timber.plant(new CrashReportingTree());
        }

        // Facebook Stetho
        Stetho.initializeWithDefaults(this);
    }
}