package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import timber.log.Timber;

public class BuildItBiggerApplication extends Application {

    private static Context context;

    public static Context getAppContext() {
        return BuildItBiggerApplication.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        BuildItBiggerApplication.context = getApplicationContext();

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