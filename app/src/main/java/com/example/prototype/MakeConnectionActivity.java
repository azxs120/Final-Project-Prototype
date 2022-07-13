package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MakeConnectionActivity extends AppCompatActivity  {
    private EditText fromDate;
    private EditText toDate;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    DatePickerDialog.OnDateSetListener onDateSetListener2;
    private RadioGroup identityGroup;
    private RadioButton tenant, homeOwner;
    private String identity = "tenant", txtNote = "", userEmail = "", mobileNumber = "",otherMobileNumber, txtFromDate,txtToDate;
    private Button sendButton;
    private EditText messageBox;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_connection);
        this.setTitle("Make Connection");

        fromDate = findViewById(R.id.fromDate);
        toDate = findViewById(R.id.toDate);
        final Calendar calendarFrom = Calendar.getInstance();
        final int yearFrom = calendarFrom.get(Calendar.YEAR);
        final int monthFrom = calendarFrom.get(Calendar.MONTH);
        final int dayFrom = calendarFrom.get(Calendar.DAY_OF_MONTH);
        final Calendar calendarTo = Calendar.getInstance();
        final int yearTo = calendarTo.get(Calendar.YEAR);
        final int monthTo = calendarTo.get(Calendar.MONTH);
        final int dayTo = calendarTo.get(Calendar.DAY_OF_MONTH);

        sendButton = (Button) findViewById(R.id.sendButton);
        identityGroup = findViewById(R.id.identityGroup);
        tenant = findViewById(R.id.tenant);
        homeOwner = findViewById(R.id.homeOwner);
        messageBox = findViewById(R.id.messageBox);
        //get the user email
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("key") != null)
            userEmail = bundle.getString("key");
        //get the other side phone
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2.getString("key1") != null)
            otherMobileNumber = bundle.getString("key1");

        // Initialize Firebase Auth
        mAuth = FirebaseConnection.getFirebaseAuth();

        // Initialize Firebase Firestore
        db = FirebaseConnection.getFirebaseFirestore();

        fromDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MakeConnectionActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener, yearFrom, monthFrom, dayFrom);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MakeConnectionActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener2, yearTo, monthTo, dayTo);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                fromDate.setText(date);

            }
        };

        onDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                toDate.setText(date);
            }
        };

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //run on the rows of the table
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String emailFromDB = doc.getString("Email").trim();//get the email of every user

                                //the emails match
                                if (userEmail.equals(emailFromDB)) {
                                    mobileNumber = doc.getString("Mobile Number");//get the MobileNumber.
                                    identity = doc.getString("Identity");//get the identity
                                    Toast.makeText(MakeConnectionActivity.this, userEmail, Toast.LENGTH_SHORT).show();

                                    if (identity.equals("tenantAndHomeOwner"))
                                        identityGroup.setVisibility(View.VISIBLE);//show me my options
                                    break;//done searching
                                }
                            }
                        }
                    }
                });



        identityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override//change the radioGroup
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tenant:
                        identity = "tenant";
                        break;
                    case R.id.homeOwner:
                        identity = "homeOwner";
                        break;
                }
            }
        });

        submitConnection();
    }


    /**
     * a method that puts the new connection in a table named "Connections"
     */
    private void submitConnection() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtNote = messageBox.getText().toString().trim();
                txtFromDate = fromDate.getText().toString().trim();
                txtToDate = toDate.getText().toString().trim();
                //create an map object for the DB
                Map<String, Object> connection = new HashMap<>();
                connection.put("Start Date", txtFromDate);
                connection.put("End Date", txtToDate);
                connection.put("Notes", txtNote);
                connection.put("Identity", identity);
                connection.put(identity + " Mobile Number", mobileNumber);//the user mobileNumber

                if (identity.equals("tenant"))
                    connection.put("homeOwner Mobile Number", otherMobileNumber);//the user mobileNumber
                else if (identity.equals("homeOwner"))
                    connection.put("tenant Mobile Number", otherMobileNumber);//the user mobileNumber
                else {//tenantAndHomeOwner
                    getOtherIdentity();
                    connection.put(identity + " Mobile Number", otherMobileNumber);
                }




                db.collection("links")
                        .add(connection)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MakeConnectionActivity.this, "Data Created Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MakeConnectionActivity.this, CallHandlingActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MakeConnectionActivity.this, "----- Error ----- the call was not created" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                //for the dev
                //Toast.makeText(MakeConnectionActivity.this, "s " + mobileNumber, Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * a method that returns the identity of the other side
     */
    private void getOtherIdentity() {
        identityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override//change the radioGroup
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tenant:
                        identity = "tenant";
                        break;
                    case R.id.homeOwner:
                        identity = "homeOwner";
                        break;
                }
            }
        });
    }




}

