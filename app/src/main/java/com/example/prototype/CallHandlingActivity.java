package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;

import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.prototype.ui.main.SectionsPagerAdapter;
import com.example.prototype.databinding.ActivityCallHandlingBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import io.realm.Realm;

public class CallHandlingActivity extends AppCompatActivity {
    private String userEmail = null;
    private ActivityCallHandlingBinding binding;
    String userMobileNumber, userIdentity;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCallHandlingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get the user email
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("userEmail") != null)
            userEmail = bundle.getString("userEmail");

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

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
                                    userMobileNumber = doc.getString("Mobile Number");//get the MobileNumber.
                                    userIdentity = doc.getString("Identity");//get the identity
                                    break;//done searching
                                }
                            }
                        }
                    }
                });

        //get the button
        FloatingActionButton addNewCall = binding.addNewCallBtn;
        //set a Click Listener
        addNewCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //open the "AddNewCall" Activity.
                    Intent intentLoadNewActivity = new Intent(CallHandlingActivity.this, AddNewCall.class);
                    intentLoadNewActivity.putExtra("userEmail", userEmail);//take the email to AddNewCall
                    intentLoadNewActivity.putExtra("userMobileNumber", userMobileNumber);//take the email to AddNewCall
                    intentLoadNewActivity.putExtra("identity", userIdentity);//take the email to AddNewCall

                    startActivity(intentLoadNewActivity);
                }
        });
    }

}