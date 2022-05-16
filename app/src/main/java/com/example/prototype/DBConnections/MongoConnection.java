package com.example.prototype.DBConnections;




import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoDatabase;

public class MongoConnection extends AppCompatActivity {
    private static MongoDatabase mongoDatabase;
    private static MongoConnection mongoConnection = null;
    private static App app;
    /**
     * a private constructor for the singleton design pattern
     */
    private MongoConnection(){
        Realm.init(this);
    }

    /**
     * if the connection exists the method will return it
     * else the method will create one and return it
     *
     * uses  mongoClient.getDatabase("RentMe")
     *
     * @return a connection to the database
     */
    public static MongoDatabase getConnection() {
        if(mongoConnection == null) {
            final String appId = "application-1-sfnjp";
            MongoClient mongoClient;
            app = new App(new AppConfiguration.Builder(appId).build());

            mongoConnection = new MongoConnection();

            //login anonymous to app
            app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
                @Override
                public void onResult(App.Result<User> result) {
                    if (result.isSuccess()) {
                        Log.v("User", "Logged In anonymously");
                    } else
                        Log.v("User", "Failed to Login");
                }
            });

            User user = app.currentUser();
            mongoClient = user.getMongoClient("mongodb-atlas");
            mongoDatabase = mongoClient.getDatabase("RentMe");//the cluster(project)
            return mongoDatabase;
        }
        return mongoDatabase;
    }

    public static App getApp(){
        return app;
    }
    public static User getUser(){
        return app.currentUser();
    }
}
