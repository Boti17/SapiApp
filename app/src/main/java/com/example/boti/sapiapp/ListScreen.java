package com.example.boti.sapiapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListScreen extends AppCompatActivity {

    private ArrayList<Advertiser> advertisers;
    private String title;
    private String description;
    private ArrayList<String> photos;
    private Advertiser advertiser;
    private Context context = this;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_screen);

        new LongOperation().execute("");
    }

    public void setUpRecyclerView()
    {
        advertisers = new ArrayList<Advertiser>();

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Advertisers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()){
                    photos=new ArrayList<String>();
                    title=childSnapshot.child("Title").getValue().toString();
                    description=childSnapshot.child("Description").getValue().toString();
                    String photoSnapshot=childSnapshot.child("Photos").getValue().toString();
                    photos=findPhoto(photoSnapshot);
                    advertiser=new Advertiser(description,title,photos);
                    advertisers.add(advertiser);
                }
                setUpAdapter(advertisers);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public ArrayList<String> findPhoto(String from){
        int position=0;
        ArrayList<String> photos = new ArrayList<String>();
        while (from.charAt(position)!='}')
        {
            while (from.charAt(position)!='=')
            {
                position++;
            }
            position++;
            String to="";
            while (from.charAt(position)!=',' && from.charAt(position)!='}'){
                to+=from.charAt(position);
                position++;
            }
            photos.add(to);
        }
        return photos;
    }

    public void setUpAdapter(final ArrayList<Advertiser> advertisers)
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        AdvertiserAdapter adapter = new AdvertiserAdapter(context,advertisers);
        recyclerView.setAdapter(adapter);

    }

    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            setUpRecyclerView();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            mProgress.dismiss();
        }

        @Override
        protected void onPreExecute() {
            mProgress = new ProgressDialog(context);
            mProgress.setMessage("Loading advertisers...");
            mProgress.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
