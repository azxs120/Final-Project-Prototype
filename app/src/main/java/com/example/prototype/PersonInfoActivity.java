package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PersonInfoActivity extends AppCompatActivity {
    private TextView idNumber;
    private Button HistoryBtn;
    private Button ConnectionBtn;
    private String userEmail = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        //save the id from FindPersonActivity
        idNumber = findViewById(R.id.UserID);

        //get the user email
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("key") != null)
            userEmail = bundle.getString("key");

        Intent intent = getIntent();
        String id = intent.getStringExtra("keyId");
        idNumber.setText(id);
        ConnectionBtn = (Button) findViewById(R.id.ConnectionButton);
        HistoryBtn = (Button) findViewById(R.id.HistoryButton);

        //set what happens when the user clicks Make Connection
        ConnectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(PersonInfoActivity.this, MakeConnectionActivity.class);
                intent.putExtra("key", userEmail);//take the email to CallHandlingActivity
                startActivity(intentLoadNewActivity);
            }
        });

        //set what happens when the user clicks view history
        HistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(PersonInfoActivity.this, ViewHistoryActivity.class);
                intent.putExtra("key", userEmail);//take the email to CallHandlingActivity
                startActivity(intentLoadNewActivity);
            }
        });
    }
}