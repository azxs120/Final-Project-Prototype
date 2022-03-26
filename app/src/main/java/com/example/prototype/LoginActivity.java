package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;


public class LoginActivity extends AppCompatActivity {

    String appId = "application-1-sfnjp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginBtn;
        Button register;
        ImageView fbBtn = findViewById(R.id.fb_btn);
        //CallbackManager callbackManager = CallbackManager.Factory.create();

        //init app
        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(appId).build());

        app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if(result.isSuccess()){
                    Log.v("User","Logged In anonymously");
                }
                else{
                    Log.v("User","Failed to Login");
                }
            }
        });



        this.setTitle("התחברות");



        loginBtn =  findViewById(R.id.loginBtn);
        //set what happens when the user clicks "מצא דירה"
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intentLoadNewActivity);
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