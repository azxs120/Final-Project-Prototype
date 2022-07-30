package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.prototype.DBClasess.Apartment;
import com.example.prototype.DBClasess.Call;

import java.util.ArrayList;

public class RelevantCalls extends AppCompatActivity {
    EditText subject, messageBody, startDate, endDate, homeOwnerPhoneNumber;
    ArrayList<Call> callsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relevant_calls);
        this.setTitle("Apartment History");

        callsList = (ArrayList<Call>) getIntent().getSerializableExtra("relevantCalls");
        subject = (EditText) findViewById(R.id.subject);
        messageBody = (EditText) findViewById(R.id.messageBody);
        startDate = (EditText) findViewById(R.id.startDate);
        endDate = (EditText) findViewById(R.id.endDate);
        homeOwnerPhoneNumber = (EditText) findViewById(R.id.homeOwnerPhoneNumber);

       // subject.setText(callsList.getSubject());
        //messageBody.setText(callBody);
       // startDate.setText(startDateDataMember);
       // endDate.setText(endDteDataMember);
       // homeOwnerPhoneNumber.setText(homeOwnerPhoneNumber);


    }
}