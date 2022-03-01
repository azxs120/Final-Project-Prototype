package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddNewCall extends AppCompatActivity {
    private EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button newCallBtn;
        EditText theText;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_call);
        this.setTitle("פתח קריאה חדשה");
        title   = (EditText)findViewById(R.id.subject);
        theText = (EditText)findViewById(R.id.messageBody);

        int duration = Toast.LENGTH_SHORT;


        newCallBtn = (Button) findViewById(R.id.submitCall);
        //set what happens when the user clicks
        newCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, title.getText().toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Toast toast = Toast.makeText(getApplicationContext(), title.getText().toString(), duration);
                toast.show();

                // how to get the text from the textBox  mEdit.getText().toString()
                Intent intentLoadNewActivity = new Intent(AddNewCall.this, CallHandlingActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
    }
}