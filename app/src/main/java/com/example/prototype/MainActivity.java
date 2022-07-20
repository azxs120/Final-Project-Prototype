package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototype.DBConnections.FirebaseConnection;
import com.example.prototype.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private ImageButton homeBtn,callHandlingBtn, myHistoryBtn, searchBtn, reviewBtn, publishApartmentBtn ;
    private String userEmail = null, userMobilNumber= null, identity = null;
    private TextView publishApartmentText;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    ActivityMainBinding binding; //for the menu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get the user email
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("userEmail") != null)
            userEmail = bundle.getString("userEmail");
        bundle = getIntent().getExtras();
        if (bundle.getString("identity") != null)
            identity = bundle.getString("identity");
        bundle = getIntent().getExtras();
        if (bundle.getString("mobileNumber") != null)
            userMobilNumber = bundle.getString("mobileNumber");

        //wire up the button to do stuff
        //get the btn
        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        callHandlingBtn = (ImageButton) findViewById(R.id.callBtn);
        myHistoryBtn = (ImageButton) findViewById(R.id.myHistoryBtn);
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        reviewBtn= (ImageButton) findViewById(R.id.review);
        publishApartmentBtn = (ImageButton) findViewById(R.id.publishApartmentBtn);
        publishApartmentText= findViewById(R.id.publishApartmentText);
        db = FirebaseConnection.getFirebaseFirestore();
        mAuth = FirebaseAuth.getInstance();

        if (identity.equals("homeOwner")||identity.equals("tenantAndHomeOwner"))
        {
            publishApartmentBtn.setVisibility(View.VISIBLE);
            publishApartmentText.setVisibility(View.VISIBLE);
        }
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
                intentLoadNewActivity.putExtra("userEmail", userEmail);//take the email to CallHandlingActivity
                intentLoadNewActivity.putExtra("identity", identity);//take the email to CallHandlingActivity
                intentLoadNewActivity.putExtra("userMobilNumber", userMobilNumber);//take the email to CallHandlingActivity
                startActivity(intentLoadNewActivity);
            }
        });

        //set what happens when the user clicks"My History"
        myHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, MyHistory.class);
                intentLoadNewActivity.putExtra("mobileNumber", userMobilNumber);//take the email to CallHandlingActivity
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

        //PersonReview
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, PersonReview.class);
                intentLoadNewActivity.putExtra("userEmail", userEmail);
                intentLoadNewActivity.putExtra("identity", identity);
                intentLoadNewActivity.putExtra("userMobilNumber", userMobilNumber);//take the mobile to PersonReviewActivity
                startActivity(intentLoadNewActivity);
            }
        });

        //set what happens when the user clicks "Publish Apartment"
        publishApartmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, PublishApartment.class);
                intentLoadNewActivity.putExtra("userMobilNumber", userMobilNumber);//take the mobile to publishApartmentActivity
                startActivity(intentLoadNewActivity);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.settings:
                Toast.makeText(this, "Settings is clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout:
                mAuth.signOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                finish();
                Toast.makeText(MainActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                break;
        }



        return super.onOptionsItemSelected(item);
    }
}