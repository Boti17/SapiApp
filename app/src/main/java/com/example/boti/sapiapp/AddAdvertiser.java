package com.example.boti.sapiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

public class AddAdvertiser extends AppCompatActivity {

    private EditText mTitle;
    private EditText mDescription;
    private Firebase mRootRef;
    private Button mSend;
    private Button mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advertiser);

        mRootRef = new Firebase("https://sapiapp-5a8e2.firebaseio.com/Advertisers");
        mTitle = (EditText) findViewById(R.id.titleEt);
        mDescription = (EditText) findViewById(R.id.descriptionEt);
        mSend = (Button) findViewById(R.id.sendButton);
        mLocation = (Button) findViewById(R.id.locationButton);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long timestamp = System.currentTimeMillis()/1000;
                String ts = timestamp.toString();
                Firebase mRefAdvertiser = mRootRef.child(ts);
                //mRefAdvertiser.setValue("Advertiser");
                Firebase mRefChild = mRefAdvertiser.child("Title");
                mRefChild.setValue(mTitle.getText().toString());
                mRefChild = mRefAdvertiser.child("Description");
                mRefChild.setValue(mDescription.getText().toString());
            }
        });

        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
