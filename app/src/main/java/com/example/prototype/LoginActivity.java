package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;




public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginBtn;
        Button register;
        ImageView fbBtn = findViewById(R.id.fb_btn);
        //CallbackManager callbackManager = CallbackManager.Factory.create();

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