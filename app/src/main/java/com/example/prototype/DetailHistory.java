package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DetailHistory extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String callBody, callSubject, homeOwnerCallStatusDataMember, tenantCallStatusDataMember, startDateDataMember, endDteDataMember;
    private Spinner spinnerStatus;
    private String userIdentity = null, docId, callId, showStatus;
    private Button updateStatusBtn;
    EditText subject, messageBody, startDate, endDate, homeOwnerCallStatus, tenantCallStatus;
    TextView changeStatusText;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);
        this.setTitle("history");

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("callBody") != null)
            callBody = bundle.getString("callBody");

        if (bundle.getString("callSubject") != null)
            callSubject = bundle.getString("callSubject");

        if (bundle.getString("homeOwnerCallStatus") != null)
            homeOwnerCallStatusDataMember = bundle.getString("homeOwnerCallStatus");

        if (bundle.getString("tenantCallStatus") != null)
            tenantCallStatusDataMember = bundle.getString("tenantCallStatus");

        if (bundle.getString("startDate") != null)
            startDateDataMember = bundle.getString("startDate");

        if (bundle.getString("endDate") != null)
            endDteDataMember = bundle.getString("endDate");
        //get the identity of the user

        if (bundle.getString("identity") != null)
            userIdentity = bundle.getString("identity");

        if (bundle.getString("docId") != null)
            docId = bundle.getString("docId");

        if (bundle.getString("callId") != null)
            callId = bundle.getString("callId");

        if (bundle.getString("statusToShow") != null)
            showStatus = bundle.getString("statusToShow");

        subject = (EditText) findViewById(R.id.subject);
        messageBody = (EditText) findViewById(R.id.messageBody);
        startDate = (EditText) findViewById(R.id.startDate);
        endDate = (EditText) findViewById(R.id.endDate);
        homeOwnerCallStatus = (EditText) findViewById(R.id.homeOwnerCallStatus);
        tenantCallStatus = (EditText) findViewById(R.id.tenantCallStatus);
        updateStatusBtn = (Button) findViewById(R.id.updateStatusBtn);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        changeStatusText = findViewById(R.id.changeStatusText);
        spinnerStatus.setOnItemSelectedListener(this);
        subject.setText(callSubject);
        messageBody.setText(callBody);
        startDate.setText(startDateDataMember);
        endDate.setText(endDteDataMember);
        homeOwnerCallStatus.setText(homeOwnerCallStatusDataMember);
        tenantCallStatus.setText(tenantCallStatusDataMember);
        // Initialize Firebase Auth
        mAuth = FirebaseConnection.getFirebaseAuth();
        db = FirebaseConnection.getFirebaseFirestore();
        reference = FirebaseDatabase.getInstance().getReference("calls");
//option to change status when you in callHeadlingActivity
        if (showStatus.equals("yes")) {
            spinnerStatus.setVisibility(View.VISIBLE);
            updateStatusBtn.setVisibility(View.VISIBLE);
            changeStatusText.setVisibility(View.VISIBLE);

        }
        updateStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusIsChanged()) {
                    Toast.makeText(DetailHistory.this, "Data has been updated", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public boolean statusIsChanged() {
        if (!userIdentity.equals(spinnerStatus.toString())) {
            db.collection("calls")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                //run on the rows of the table
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    String callIdFromDB = doc.getString("Call Id");
                                    String startDateFromDB = doc.getString("Start Date");

                                    if (callIdFromDB != null) {
                                        if (userIdentity.equals("tenant"))
                                            if (callIdFromDB.equals(callId)) {
                                                String newStatus = spinnerStatus.getSelectedItem().toString();

                                                db.collection("calls").document(doc.getId()).
                                                        update("Tenant Call Status", newStatus);
                                                if (newStatus.equals("closed")) {
                                                    //get current date
                                                    Calendar calendar = Calendar.getInstance();
                                                    String txtCurrentDate = DateFormat.getDateInstance().format(calendar.getTime());
                                                    //set the end Date
                                                    db.collection("calls").document(doc.getId()).
                                                            update("End Date", txtCurrentDate);
                                                }
                                            }
                                        if (userIdentity.equals("homeOwner"))
                                            if (callIdFromDB.equals(callId)) {
                                                String newStatus = spinnerStatus.getSelectedItem().toString();

                                                db.collection("calls").document(doc.getId()).
                                                        update("Homeowner Call Status", newStatus);
                                                if (newStatus.equals("closed")) {
                                                    //get current date
                                                    Calendar calendar = Calendar.getInstance();
                                                    String txtCurrentDate = DateFormat.getDateInstance().format(calendar.getTime());
                                                    //set the end Date
                                                    db.collection("calls").document(doc.getId()).
                                                            update("End Date", txtCurrentDate);

                                                }
                                            }

                                    }
                                }
                            }


                        }
                    });

            return true;
        } else
            return false;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


