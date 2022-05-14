package com.example.prototype.DBConnections;



import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototype.LoginActivity;
import com.example.prototype.RegisterActivity;
import com.google.android.material.snackbar.Snackbar;

import org.bson.Document;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoDatabase;


public class MongoConnection extends AppCompatActivity {

    public MongoDatabase getConnection() {
        final String appId = "application-1-sfnjp";
        MongoDatabase mongoDatabase;
        MongoClient mongoClient;
        App app = new App(new AppConfiguration.Builder(appId).build());

        Realm.init(this);
        //login to app
        app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if(result.isSuccess()){
                    Log.v("User", "Logged In anonymously");
                }else
                    Log.v("User", "Failed to Login");
            }
        });

        User user = app.currentUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("RentMe");//the cluster(project)
        return mongoDatabase;
    }
}
