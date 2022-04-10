package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.snackbar.Snackbar;

import org.bson.Document;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;


public class LoginActivity extends AppCompatActivity {
    //this is the connection string;
    private String appId = "application-1-sfnjp";

    //DB instance
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Login");

        Button loginBtn;
        Button register;
        ImageView fbBtn = findViewById(R.id.fb_btn);

        //will contain the data that we want to add
        EditText emailEditText = (EditText) findViewById(R.id.email);
        EditText passwordEditText = (EditText) findViewById(R.id.editTextPassword);
        //init app
        Realm.init(this);
        //this will build a new app object
        App app = new App(new AppConfiguration.Builder(appId).build());



        loginBtn =  findViewById(R.id.loginBtn);
        //set what happens when the user clicks "Login"
        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //DB code
                Credentials credentials = Credentials.emailPassword(emailEditText.getText().toString(), passwordEditText.getText().toString());
                app.loginAsync(credentials, new App.Callback<User>() {
                    @Override
                    public void onResult(App.Result<User> result) {

                    }
                });

                User user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("RentMe");//the cluster(project)

                Document doc = new Document() ;
                doc.append("Email", emailEditText.getText().toString());
                doc.append("Password", passwordEditText.getText().toString());

                mongoDatabase.getCollection("Person").insertOne(doc).getAsync(result -> {
                    if(result.isSuccess()) {
                        Log.v("Data", "Data Inserted Successfully");
                        Snackbar.make(view, "Data Inserted Successfully", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else
                        Log.v("Data","our Error " + result.getError().toString());
                });


                //create a new activity
                Intent intentLoadNewActivity = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intentLoadNewActivity);//start the new activity.


            }
        });

        register =  findViewById(R.id.Register);
        //set what happens when the user clicks "מצא דירה"
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });

        fbBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //login to facebook

            }
        });
    }
}