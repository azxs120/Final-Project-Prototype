package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ConnectionString connectionString = new ConnectionString("mongodb+srv://<username>:<password>@asyahezi.0fmm4.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        //MongoClientSettings settings = MongoClientSettings.builder()
        //        .applyConnectionString(connectionString)
        //        .build();
        //MongoClient mongoClient = MongoClients.create(settings);
        //MongoDatabase database = mongoClient.getDatabase("test");



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.setTitle("מצא דירה");
    }
}