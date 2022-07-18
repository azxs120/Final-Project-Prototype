package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DetailHistory extends AppCompatActivity {
    private String callBody, callSubject,homeOwnerCallStatus,tenantCallStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("callBody") != null)
            callBody = bundle.getString("callBody");
        Bundle bundle1 = getIntent().getExtras();
        if (bundle1.getString("callSubject") != null)
            callSubject = bundle.getString("callSubject");
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2.getString("homeOwnerCallStatus") != null)
            homeOwnerCallStatus = bundle.getString("homeOwnerCallStatus");
        Bundle bundle3 = getIntent().getExtras();
        if (bundle3.getString("tenantCallStatus") != null)
            tenantCallStatus = bundle.getString("tenantCallStatus");
    }
}