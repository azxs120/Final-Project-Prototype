package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoDatabase;

public class MainActivity extends AppCompatActivity {
    private ImageButton homeBtn;
    private ImageButton callHandlingBtn;
    private ImageButton myHistoryBtn;
    private ImageButton searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String userEmail = null;

        //get the user Email from the previous activity
        Bundle extras = getIntent().getExtras();//get all of the extra data
        if(extras != null)
            userEmail = extras.getString("userEmail");//extract the specific data we need
        String finalUserEmail = userEmail;//put it in a final variable

        //wire up the button to do stuff
        //get the btn
        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        callHandlingBtn = (ImageButton) findViewById(R.id.callBtn);
        myHistoryBtn = (ImageButton) findViewById(R.id.myHistoryBtn);
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);



        //set what happens when the user clicks "Find Apartment"
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });

        //set what happens when the user clicks "Call Handling"
        callHandlingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, CallHandlingActivity.class);
                intentLoadNewActivity.putExtra("userEmail", finalUserEmail);
                startActivity(intentLoadNewActivity);
            }
        });

        //set what happens when the user clicks"My History"
        myHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, MyHistory.class);
                startActivity(intentLoadNewActivity);
            }
        });

        //set what happens when the user clicks"Find Person"
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, FindPersonActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });

    }

}