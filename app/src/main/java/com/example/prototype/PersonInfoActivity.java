package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PersonInfoActivity extends AppCompatActivity {
    private TextView idNumber;
    private Button HistoryButton;
    private Button ConnectionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        //save the id from FindPersonActivity
        idNumber = findViewById(R.id.UserID);

        Intent intent = getIntent();
        String id = intent.getStringExtra("keyId");
        idNumber.setText(id);
        ConnectionButton=(Button) findViewById(R.id.ConnectionButton);
        HistoryButton=(Button) findViewById(R.id.HistoryButton);
        //set what happens when the user clicks view history
        ConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(PersonInfoActivity.this, ViewHistoryActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });

        HistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(PersonInfoActivity.this, ViewHistoryActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
    }
}