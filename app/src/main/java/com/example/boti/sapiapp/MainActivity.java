package com.example.boti.sapiapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Intent i = new Intent(this,AddAdvertiser.class);
        startActivity(i);*/
        /*Intent i = new Intent(this,ListScreen.class);
        startActivity(i);*/
        Intent i = new Intent(this,Registration.class);
        startActivity(i);
    }
}
