package com.example.boti.sapiapp;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Boti on 2017.10.28..
 */

public class SapiApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
