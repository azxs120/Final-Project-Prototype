package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewHistoryActivity extends AppCompatActivity {
    private String otherUserPhoneNumber = null;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        this.setTitle(otherUserPhoneNumber + " history");

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("otherUserPhoneNumber") != null)
            otherUserPhoneNumber = bundle.getString("otherUserPhoneNumber");

        db = FirebaseConnection.getFirebaseFirestore();


        db.collection("calls")//go to users table
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {//אם הגישה לטבלה הצליחה
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {//תביא את כל הרשומות
                                String tenantMobileNumber = doc.getString("tenant Mobile Number");
                                String homeOwnerMobileNumber = doc.getString("homeOwner Mobile Number");

                                if (tenantMobileNumber.equals(otherUserPhoneNumber) ||
                                        homeOwnerMobileNumber.equals(otherUserPhoneNumber)) {
                                    //TODO get all of the call data
                                    String callBody = doc.getString("Call Body");
                                    String callSubject = doc.getString("Subject");
                                    String homeOwnerCallStatus = doc.getString("Home owner Call Status");
                                    String tenantCallStatus = doc.getString("Tenant Call Status");
                                    //String startDate = doc.getString("Start Date");
                                    //String homeOwnerMobileNumber = doc.getString("homeOwner Mobile Number");
                                    //String tenantMobileNumber = doc.getString("tenant Mobile Number");



                                }


                            }
                        }
                    }
                });


    }
}