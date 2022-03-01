package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

public class AddNewCall extends AppCompatActivity {
    private Button newCallBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_call);


        this.setTitle("פתח קריאה חדשה");

        newCallBtn = (Button) findViewById(R.id.submitCall);
        //set what happens when the user clicks "מצא דירה"
        newCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "פיצ'ר עתידי, כפתור זה יוסיף קריאה חדשה לטאב של הקריאות הפתוחות.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intentLoadNewActivity = new Intent(AddNewCall.this, CallHandlingActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
    }
}