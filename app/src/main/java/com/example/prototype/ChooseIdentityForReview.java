package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ChooseIdentityForReview extends AppCompatActivity {
    Button moveToNewCall;
    private String userEmail = null, otherUserPhoneNumber;
    private String userMobileNumber = null;
    private String userIdentity = null;
    private String otherIdentity = null, otherNumber = null;
    private RadioButton tenant, homeOwner;
    private RadioGroup radioGroup;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_identity_for_review);
        radioGroup = findViewById(R.id.radioGroup);
        tenant = findViewById(R.id.tenant);
        homeOwner = findViewById(R.id.homeOwner);
        moveToNewCall = (Button) findViewById(R.id.submitCall);

        //get the user email
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("userEmail") != null)
            userEmail = bundle.getString("userEmail");
        bundle = getIntent().getExtras();
        if (bundle.getString("userMobileNumber") != null)
            userMobileNumber = bundle.getString("userMobileNumber");
        bundle = getIntent().getExtras();
        if (bundle.getString("otherUserPhoneNumber") != null)
            otherUserPhoneNumber = bundle.getString("otherUserPhoneNumber");
        bundle = getIntent().getExtras();
        if (bundle.getString("identity") != null)
            userIdentity = bundle.getString("identity");
        bundle = getIntent().getExtras();
        if (bundle.getString("review About") != null)
            otherNumber = bundle.getString("review About");


        getMyIdentity();

        moveToNewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //open the "AddNewCall" Activity.
                Intent intentLoadNewActivity = new Intent(ChooseIdentityForReview.this, WriteReview.class);
                intentLoadNewActivity.putExtra("userEmail", userEmail);//take the email to AddNewCall
                intentLoadNewActivity.putExtra("userMobileNumber", userMobileNumber);//take the email to AddNewCall
                intentLoadNewActivity.putExtra("otherUserPhoneNumber", otherUserPhoneNumber);//take the email to AddNewCall
                intentLoadNewActivity.putExtra("identity", userIdentity);//take the email to AddNewCall
                intentLoadNewActivity.putExtra("review about", otherNumber);//take the email to AddNewCall

                startActivity(intentLoadNewActivity);
            }
        });
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
