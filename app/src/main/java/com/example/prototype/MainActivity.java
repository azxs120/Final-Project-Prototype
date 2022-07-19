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
    private ImageButton apartmentSearchBtn;
    private ImageButton callHandlingBtn;
    private ImageButton myHistoryBtn;
    private ImageButton searchBtn;
    private String userEmail = null, userMobileNumber;
    private Button logoutBtn;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    String identity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the user email
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("userEmail") != null)
            userEmail = bundle.getString("userEmail");
        bundle = getIntent().getExtras();
        if (bundle.getString("identity") != null)
            identity = bundle.getString("identity");
        bundle = getIntent().getExtras();
        if (bundle.getString("mobileNumber") != null)
            userMobileNumber = bundle.getString("mobileNumber");

        //wire up the button to do stuff
        //get the btn
        apartmentSearchBtn = (ImageButton) findViewById(R.id.homeBtn);
        callHandlingBtn = (ImageButton) findViewById(R.id.callBtn);
        myHistoryBtn = (ImageButton) findViewById(R.id.myHistoryBtn);
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        db = FirebaseConnection.getFirebaseFirestore();
        mAuth = FirebaseAuth.getInstance();

        //set what happens when the user clicks "Logout"
        logoutBtn = findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
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
        apartmentSearchBtn.setOnClickListener(new View.OnClickListener() {
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
                intentLoadNewActivity.putExtra("userEmail", userEmail);//take the email to CallHandlingActivity
                intentLoadNewActivity.putExtra("identity", identity);//take the email to CallHandlingActivity

                startActivity(intentLoadNewActivity);
            }
        });

        //set what happens when the user clicks"My History"
        myHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, MyHistory.class);
                intentLoadNewActivity.putExtra("mobileNumber", userMobileNumber);//take the email to CallHandlingActivity

                startActivity(intentLoadNewActivity);
            }
        });

        //set what happens when the user clicks"Find Person"
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, FindPersonActivity.class);
                intentLoadNewActivity.putExtra("userEmail", userEmail);//take the email to CallHandlingActivity
                intentLoadNewActivity.putExtra("identity", identity);//take the email to CallHandlingActivity
                startActivity(intentLoadNewActivity);
            }
        });

    }

}