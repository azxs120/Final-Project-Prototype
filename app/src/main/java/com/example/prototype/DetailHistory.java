package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DetailHistory extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String callBody, callSubject,homeOwnerCallStatusDataMember,tenantCallStatusDataMember, startDateDataMember, endDteDataMember;
    private Spinner spinnerStatus;

    EditText subject, messageBody, startDate, endDate, homeOwnerCallStatus, tenantCallStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);
        this.setTitle("history");

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("callBody") != null)
            callBody = bundle.getString("callBody");
        Bundle bundle1 = getIntent().getExtras();
        if (bundle1.getString("callSubject") != null)
            callSubject = bundle.getString("callSubject");
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2.getString("homeOwnerCallStatus") != null)
            homeOwnerCallStatusDataMember = bundle.getString("homeOwnerCallStatus");
        Bundle bundle3 = getIntent().getExtras();
        if (bundle3.getString("tenantCallStatus") != null)
            tenantCallStatusDataMember = bundle.getString("tenantCallStatus");
        Bundle bundle4 = getIntent().getExtras();
        if (bundle4.getString("startDate") != null)
            startDateDataMember = bundle.getString("startDate");
        Bundle bundle5 = getIntent().getExtras();
        if (bundle5.getString("endDate") != null)
            endDteDataMember = bundle.getString("endDate");



        subject =  (EditText) findViewById(R.id.subject);
        messageBody = (EditText) findViewById(R.id.messageBody);
        startDate = (EditText) findViewById(R.id.startDate);
        endDate  = (EditText) findViewById(R.id.endDate);
        homeOwnerCallStatus  = (EditText) findViewById(R.id.homeOwnerCallStatus);
        tenantCallStatus  = (EditText) findViewById(R.id.tenantCallStatus);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        spinnerStatus.setOnItemSelectedListener(this);
        subject.setText(callSubject);
        messageBody.setText(callBody);
        startDate.setText(startDateDataMember);
        endDate.setText(endDteDataMember);
        homeOwnerCallStatus.setText(homeOwnerCallStatusDataMember);
        tenantCallStatus.setText(tenantCallStatusDataMember);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}