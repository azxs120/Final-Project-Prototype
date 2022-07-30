package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;

import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.prototype.databinding.ActivityCallHandlingBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CallHandlingActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityCallHandlingBinding binding;
    private String userEmail = null, userMobilNumber= null, identity = null;
    private FirebaseFirestore db;
    private Button ongoingCalls;
    private Button closedCallsTab;
    private Button handlingCalls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCallHandlingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ongoingCalls = findViewById(R.id.OngoingCalls);
        closedCallsTab = findViewById(R.id.ClosedCallsTab);
        handlingCalls = findViewById(R.id.HandlingCalls);

        ongoingCalls.setOnClickListener(this);
        closedCallsTab.setOnClickListener(this);
        handlingCalls.setOnClickListener(this);

        //get the user email
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("userEmail") != null)
            userEmail = bundle.getString("userEmail");
        //get the user identity
        bundle = getIntent().getExtras();
        if(bundle.getString("identity") != null)
            identity = bundle.getString("identity");
        bundle = getIntent().getExtras();
        if (bundle.getString("userMobilNumber") != null)
            userMobilNumber = bundle.getString("userMobilNumber");

        // Initialize Firebase Firestore
        db = FirebaseConnection.getFirebaseFirestore();

        //get the MobileNumber of the user
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
                                    //userMobileNumber = doc.getString("Mobile Number");//get the MobileNumber.
                                    identity = doc.getString("Identity");//get the identity
                                    break;//done searching
                                }
                            }
                        }
                    }
                });



        //get the add new call button
        FloatingActionButton addNewCall = binding.addNewCallBtn;
        addNewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (identity.equals("tenantAndHomeOwner")) {
                    //open the "AddNewCall" Activity.
                    Intent intentLoadNewActivity = new Intent(CallHandlingActivity.this, ChooseIdentity.class);
                    intentLoadNewActivity.putExtra("userEmail", userEmail);//take the email to AddNewCall
                    intentLoadNewActivity.putExtra("userMobileNumber", userMobilNumber);//take the email to AddNewCall
                    intentLoadNewActivity.putExtra("identity", identity);//take the email to AddNewCall

                    startActivity(intentLoadNewActivity);
                } else {
                    //open the "AddNewCall" Activity.
                    Intent intentLoadNewActivity = new Intent(CallHandlingActivity.this, AddNewCall.class);
                    intentLoadNewActivity.putExtra("userEmail", userEmail);//take the email to AddNewCall
                    intentLoadNewActivity.putExtra("userMobileNumber", userMobilNumber);//take the email to AddNewCall
                    intentLoadNewActivity.putExtra("identity", identity);//take the email to AddNewCall

                    startActivity(intentLoadNewActivity);
                }
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.OngoingCalls:
                Intent OngoingCalls_view = new Intent(CallHandlingActivity.this, ViewHistoryActivity.class);
                OngoingCalls_view.putExtra("otherUserPhoneNumber", userMobilNumber);//take the email to AddNewCall
                OngoingCalls_view.putExtra("status", "open");//take the email to AddNewCall
                OngoingCalls_view.putExtra("identity", identity);//take the email to AddNewCall
                startActivity(OngoingCalls_view);
                break;

            case R.id.HandlingCalls:
                Intent HandlingCalls_view = new Intent(CallHandlingActivity.this, ViewHistoryActivity.class);
                HandlingCalls_view.putExtra("otherUserPhoneNumber", userMobilNumber);//take the email to AddNewCall
                HandlingCalls_view.putExtra("status", "handling");//take the email to AddNewCall
                HandlingCalls_view.putExtra("identity", identity);//take the email to AddNewCall
                startActivity(HandlingCalls_view);
                break;
            case R.id.ClosedCallsTab:
                Intent ClosedCalls_view = new Intent(CallHandlingActivity.this, ViewHistoryActivity.class);
                ClosedCalls_view.putExtra("otherUserPhoneNumber", userMobilNumber);//take the email to AddNewCall
                ClosedCalls_view.putExtra("status", "closed");//take the email to AddNewCall
                ClosedCalls_view.putExtra("identity", identity);//take the email to AddNewCall
                startActivity(ClosedCalls_view);
                break;
        }
    }
}