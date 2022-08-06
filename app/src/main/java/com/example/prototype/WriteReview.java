package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class WriteReview extends AppCompatActivity {
    private Button sendReviewBtn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    EditText message;
    String txtMessage;
    private String txtCurrentDate, userMobileNumber, reviewAbout, userIdentity,apartmentId;

    private Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        txtCurrentDate = DateFormat.getDateInstance().format(calendar.getTime());

        mAuth = FirebaseConnection.getFirebaseAuth();
        db = FirebaseConnection.getFirebaseFirestore();

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("review about") != null)
            reviewAbout = bundle.getString("review about");
        bundle = getIntent().getExtras();
        if (bundle.getString("userMobileNumber") != null)
            userMobileNumber = bundle.getString("userMobileNumber");
        bundle = getIntent().getExtras();
        if (bundle.getString("identity") != null)
            userIdentity = bundle.getString("identity");
        if (bundle.getString("apartmentId") != null)
            apartmentId = bundle.getString("apartmentId");

        message = (EditText) findViewById(R.id.messageBody);


        sendReviewBtn = findViewById(R.id.sendReview);
        sendReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtMessage = message.getText().toString().trim();
                Map<String, Object> call = new HashMap<>();
                if(apartmentId != null)
                    call.put("Apartment ID", apartmentId);
                else
                    call.put("Apartment ID", -1);
                call.put("Body", txtMessage);
                call.put("Date", txtCurrentDate);

                //my identity
                if (userIdentity.equals("tenant")) {
                    call.put("tenant Mobile Number", userMobileNumber);
                    call.put("Review About", reviewAbout);
                } else if (userIdentity.equals("homeOwner")) {
                    call.put("homeOwner Mobile Number", userMobileNumber);
                    call.put("Review About", reviewAbout);
                } else {//tenantAndHomeOwner
                    if (userIdentity.equals("tenant")) {
                        call.put("tenant Mobile Number", userMobileNumber);
                        call.put("Review About", reviewAbout);
                    } else if (userIdentity.equals("homeOwner")) {
                        call.put("homeOwner Mobile Number", userMobileNumber);
                        call.put("Review About", reviewAbout);
                    }
                }


                db.collection("opinion")
                        .add(call)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                Toast.makeText(WriteReview.this, "opinion was recorded Successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {


                                Toast.makeText(WriteReview.this, "----- Error ----- the opinion was not recorded", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            }
        });
    }
}