package com.example.boti.sapiapp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FullScreenImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_full_screen_image);

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(this).load(image).into(imageView);
    }
}
