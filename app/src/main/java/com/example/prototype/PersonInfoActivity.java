package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class PersonInfoActivity extends AppCompatActivity {
    private TextView otherUserPhoneNumberTextView;
    private TextView otherUserNameTextView;
    private Button HistoryBtn;
    private Button ConnectionBtn;
    private FirebaseFirestore db;

    //for sending to the next intent
    private String userEmail;
    private String apartmentId;
    //for printing
    private String otherUserPhoneNumber = null;
    private String otherUserName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        //save the id from FindPersonActivity
        otherUserPhoneNumberTextView = findViewById(R.id.otherUserPhoneNumber);

        db = FirebaseConnection.getFirebaseFirestore();

        //get the Extras
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("otherUserName") != null)
            otherUserName = bundle.getString("otherUserName");
        bundle = getIntent().getExtras();
        if (bundle.getString("otherUserPhoneNumber") != null)
            otherUserPhoneNumber = bundle.getString("otherUserPhoneNumber");
        bundle = getIntent().getExtras();
        if (bundle.getString("userEmail") != null)
            userEmail = bundle.getString("userEmail");
        bundle = getIntent().getExtras();
        if (bundle.getString("apartmentId") != null)
            apartmentId = bundle.getString("apartmentId");

        this.setTitle(otherUserName + " Info");

        //TODO set text box to be name
        if(otherUserName == null)
            Toast.makeText(PersonInfoActivity.this,
                    "-----Error name is empty ----- ",
                    Toast.LENGTH_SHORT).show();

        otherUserPhoneNumberTextView.setText(otherUserPhoneNumber);
        ConnectionBtn = (Button) findViewById(R.id.ConnectionButton);
        HistoryBtn = (Button) findViewById(R.id.HistoryButton);
        otherUserPhoneNumber = otherUserPhoneNumberTextView.getText().toString().trim();
        otherUserNameTextView = findViewById(R.id.otherUserName);
        otherUserNameTextView.setText(otherUserName);

        //set what happens when the user clicks Make Connection
        ConnectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(PersonInfoActivity.this, MakeConnectionActivity.class);
                Bundle extras = new Bundle();
                extras.putString("userEmail", userEmail);
                extras.putString("otherUserPhoneNumber", otherUserPhoneNumber);
                extras.putString("apartmentId", apartmentId);

                newIntent.putExtras(extras);
                startActivity(newIntent);
            }
        });

        //set what happens when the user clicks view history
        HistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(PersonInfoActivity.this, ViewHistoryActivity.class);
                newIntent.putExtra("userEmail", userEmail);//take the email to CallHandlingActivity
                newIntent.putExtra("otherUserPhoneNumber", otherUserPhoneNumber);//take the email to CallHandlingActivity
                startActivity(newIntent);
            }
        });
    }

}