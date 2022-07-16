package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private ImageButton homeBtn;
    private ImageButton callHandlingBtn;
    private ImageButton myHistoryBtn;
    private ImageButton searchBtn;
    private String userEmail = null;
    private Button btnLogout;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the user email
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("userEmail") != null)
            userEmail = bundle.getString("userEmail");

        //wire up the button to do stuff
        //get the btn
        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        callHandlingBtn = (ImageButton) findViewById(R.id.callBtn);
        myHistoryBtn = (ImageButton) findViewById(R.id.myHistoryBtn);
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        db = FirebaseConnection.getFirebaseFirestore();
        mAuth= FirebaseAuth.getInstance();
        //set what happens when the user clicks "Logout"
        btnLogout = findViewById(R.id.logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                finish();
                Toast.makeText(MainActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
            }
        });

        //set what happens when the user clicks "Find Apartment"
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, ApartmentSearchActivity.class);
                startActivity(intentLoadNewActivity);

            }
        });

        //set what happens when the user clicks "Call Handling"
        callHandlingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, CallHandlingActivity.class);
                intentLoadNewActivity.putExtra("key", userEmail);//take the email to CallHandlingActivity
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
                intentLoadNewActivity.putExtra("userEmail", userEmail);//take the email to CallHandlingActivity
                startActivity(intentLoadNewActivity);
            }
        });

    }

}