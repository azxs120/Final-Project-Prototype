package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.prototype.DBClasess.Call;
import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AddNewCall extends AppCompatActivity {
    private EditText callTitle;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    Button newCallBtn;
    EditText message;
    String txtTitle, txtMessage, txtCurrentDate;
    Calendar calendar = Calendar.getInstance();
    private String userEmail = null;
    private String userMobileNumber = null;
    private String userIdentity = null;
    private String otherIdentity = null, otherNumber = null;
    private RadioButton tenant, homeOwner;
    private RadioGroup radioGroup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_call);
        this.setTitle("Open New Call");

        callTitle = (EditText) findViewById(R.id.subject);
        message = (EditText) findViewById(R.id.messageBody);
        newCallBtn = (Button) findViewById(R.id.submitCall);
        radioGroup = findViewById(R.id.radioGroup);
        tenant = findViewById(R.id.tenant);
        homeOwner = findViewById(R.id.homeOwner);

        //get the user email
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("userEmail") != null)
            userEmail = bundle.getString("userEmail");
        bundle = getIntent().getExtras();
        if (bundle.getString("userMobileNumber") != null)
            userMobileNumber = bundle.getString("userMobileNumber");
        bundle = getIntent().getExtras();
        if (bundle.getString("identity") != null)
            userIdentity = bundle.getString("identity");

        if (userIdentity.equals("tenantAndHomeOwner"))
            radioGroup.setVisibility(View.VISIBLE);//show me my options

        //get the other user mobile number
        // Initialize Firebase Auth
        mAuth = FirebaseConnection.getFirebaseAuth();
        db = FirebaseConnection.getFirebaseFirestore();
        db.collection("connections")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //run on the rows of the table
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String myPhoneNumberFromDB = doc.getString(userIdentity + " Mobile Number");//get the email of every user

                                //the emails match
                                if (userMobileNumber.equals(myPhoneNumberFromDB)) {
                                    if (userIdentity.equals("tenant"))
                                        otherNumber = doc.getString("homeOwner Mobile Number").trim();//get the email of every user
                                    else
                                        otherNumber = doc.getString("tenant Mobile Number").trim();//get the email of every user
                                    break;
                                }
                            }
                        }
                    }
                });

        //SignUpCall();



        newCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTitle = callTitle.getText().toString().trim();
                txtMessage = message.getText().toString().trim();
                txtCurrentDate = DateFormat.getDateInstance().format(calendar.getTime());

                Map<String, Object> call = new HashMap<>();
                call.put("Subject", txtTitle);
                call.put("Call Body", txtMessage);
                call.put("Start Date", txtCurrentDate);
                call.put("Tenant Call Status", "open");
                call.put("Home owner Call Status", "open");

                if (otherNumber != null) {

                    //my identity
                    if (userIdentity.equals("tenant")) {
                        call.put("tenant Mobile Number", userMobileNumber);
                        call.put("homeOwner Mobile Number", otherNumber);
                    } else if (userIdentity.equals("homeOwner")) {
                        call.put("homeOwner Mobile Number", userMobileNumber);
                        call.put("tenant Mobile Number", otherNumber);
                    } else {//tenantAndHomeOwner
                        getMyIdentity();
                        if (userIdentity.equals("tenant")) {
                            call.put("tenant Mobile Number", userMobileNumber);
                            call.put("homeOwner Mobile Number", otherNumber);
                        } else if (userIdentity.equals("homeOwner")) {
                            call.put("homeOwner Mobile Number", userMobileNumber);
                            call.put("tenant Mobile Number", otherNumber);
                        }
                    }

                    db.collection("calls")
                            .add(call)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(AddNewCall.this, "Call Created Successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddNewCall.this, CallHandlingActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddNewCall.this, "----- Error ----- the call was not created" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddNewCall.this, CallHandlingActivity.class);
                                    startActivity(intent);
                                }
                            });
                }else
                    Toast.makeText(AddNewCall.this, "----- Error ----- there is no connection between you" , Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void SignUpCall() {



    }

    /**
     * a method that returns the identity of the other side
     */
    private void getMyIdentity() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override//change the radioGroup
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tenant:
                        userIdentity = "tenant";
                        break;
                    case R.id.homeOwner:
                        userIdentity = "homeOwner";
                        break;
                }
            }
        });
    }

}