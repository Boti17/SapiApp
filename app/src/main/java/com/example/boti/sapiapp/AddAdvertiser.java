package com.example.boti.sapiapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class AddAdvertiser extends AppCompatActivity {

    private EditText mTitle;
    private EditText mDescription;
    private Firebase mRootRef;
    private Button mSend;
    private Button mImages;
    public static final int GALLERY_INTENT = 2;
    private Uri imageUri;
    private StorageReference mStorage;
    private Context context = this;
    private ArrayList<String> photoUris;
    private ArrayList<String> photoNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advertiser);

        photoUris= new ArrayList<String>();
        photoNames = new ArrayList<String>();

        mRootRef = new Firebase("https://sapiapp-5a8e2.firebaseio.com/Advertisers");
        mStorage = FirebaseStorage.getInstance().getReference();
        mTitle = (EditText) findViewById(R.id.titleEt);
        mDescription = (EditText) findViewById(R.id.descriptionEt);
        mSend = (Button) findViewById(R.id.sendButton);
        mImages = (Button) findViewById(R.id.imageButton);

        mImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mTitle.getText().toString().equals(""))
                {
                    Toast.makeText(context,"Please write a title",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mDescription.getText().toString().equals(""))
                {
                    Toast.makeText(context,"Please write a description",Toast.LENGTH_LONG).show();
                    return;
                }
                if (photoUris.size()==0)
                {
                    Toast.makeText(context,"Please select an image",Toast.LENGTH_LONG).show();
                    return;
                }
                Long timestamp = System.currentTimeMillis()/1000;
                String ts = timestamp.toString();
                Firebase mRefAdvertiser = mRootRef.child(ts);
                Firebase mRefChild = mRefAdvertiser.child("Title");
                mRefChild.setValue(mTitle.getText().toString());
                mRefChild = mRefAdvertiser.child("Description");
                mRefChild.setValue(mDescription.getText().toString());
                mRefChild = mRefAdvertiser.child("Photos");
                for (int i=0; i<photoUris.size(); ++i)
                {
                    String uri = photoUris.get(i);
                    String name = photoNames.get(i);
                    Firebase mRefPhotos = mRefChild.child(name);
                    mRefPhotos.setValue(uri);
                }
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode==RESULT_OK && requestCode==GALLERY_INTENT)
        {
            imageUri=data.getData();
            StorageReference filepath = mStorage.child("Photos").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri);
            Toast.makeText(context,"Photo added",Toast.LENGTH_LONG).show();
            String photoUri = imageUri.toString();
            String photoName = filepath.getName().toString();
            photoUris.add(photoUri);
            photoNames.add(photoName);
        }
    }
}
