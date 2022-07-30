package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.prototype.DBClasess.Call;

import java.util.ArrayList;

public class RelevantApartmentCallDetails extends AppCompatActivity {
    EditText subject, messageBody, startDate, endDate, homeOwnerPhoneNumber;
    ArrayList<Call> callsList = new ArrayList<>();
    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relevant_apartment_call_details);
        this.setTitle("Apartment Call History");

        callsList = (ArrayList<Call>) getIntent().getSerializableExtra("relevantCalls");

        Bundle bundle = getIntent().getExtras();
        index = bundle.getInt("index");


        callsList = (ArrayList<Call>) getIntent().getSerializableExtra("relevantCalls");
        subject = (EditText) findViewById(R.id.subject);
        messageBody = (EditText) findViewById(R.id.messageBody);
        startDate = (EditText) findViewById(R.id.startDate);
        endDate = (EditText) findViewById(R.id.endDate);
        homeOwnerPhoneNumber = (EditText) findViewById(R.id.homeOwnerPhoneNumber);

        if (index != -1) {
            subject.setText(callsList.get(index).getSubject());
            messageBody.setText(callsList.get(index).getCallBody());
            startDate.setText(callsList.get(index).getStartDate());
            endDate.setText(callsList.get(index).getEndDate());
            homeOwnerPhoneNumber.setText(callsList.get(index).getHomeOwnerMobileNumber());
        }
    }
}