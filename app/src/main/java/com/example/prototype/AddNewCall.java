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
import android.widget.Toast;


import com.example.prototype.DBClasess.Call;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AddNewCall extends AppCompatActivity {
    private EditText title;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    Button newCallBtn;
    EditText message;
    String txtTitle, txtMessage, txtCurrentDate;
    Calendar calendar = Calendar.getInstance();
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_call);
        this.setTitle("Open New Call");
        title = (EditText) findViewById(R.id.subject);
        message = (EditText) findViewById(R.id.messageBody);
        newCallBtn = (Button) findViewById(R.id.submitCall);



        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();
        //getting data from login
        Intent intent = getIntent();
        email = intent.getStringExtra("key");
        SignUpCall();
        /*int duration = Toast.LENGTH_SHORT;



        //set what happens when the user clicks
        newCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for the snooze
                createNotificationChannel();

                Toast.makeText(AddNewCall.this, "Snooze Set!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AddNewCall.this, Snooze.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddNewCall.this, 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


                long timeS = System.currentTimeMillis();
                long fiveSec = 1000 * 5;

                Date date = new Date();


                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, date.getHours());
                calendar.set(Calendar.MINUTE, date.getMinutes());
                calendar.set(Calendar.SECOND, 0);

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 5000, pendingIntent);
                //alarmManager.set(AlarmManager.RTC_WAKEUP, timeS + fiveSec, pendingIntent);

                //FOR THE REST
                //Snackbar.make(view, title.getText().toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Call call = new Call("123", "444", title.getText().toString(), theText.getText().toString());
                Toast toast = Toast.makeText(getApplicationContext(), call.toString(), duration);
                toast.show();

                // how to get the text from the textBox  mEdit.getText().toString()
                //Intent intentLoadNewActivity = new Intent(AddNewCall.this, CallHandlingActivity.class);
                //startActivity(intentLoadNewActivity);
            }
        });
    }

    /**
     * a method for the snooze
     */
   /* private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";
            String description = "channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService((NotificationManager.class));
            notificationManager.createNotificationChannel(channel);


        }*/

    }
    private void SignUpCall() {
        //progressBar.setVisibility(View.VISIBLE);
        //btnSignUp.setVisibility(View.INVISIBLE);

        newCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTitle = title.getText().toString().trim();
                txtMessage= message.getText().toString().trim();
                txtCurrentDate = DateFormat.getDateInstance().format(calendar.getTime());

                Map<String, Object> call = new HashMap<>();
                call.put("Subject", txtTitle);
                call.put("CallBody", txtMessage);
                call.put("Date", txtCurrentDate);
                call.put("Email", email);


                db.collection("calls")
                        .add(call)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(AddNewCall.this, "Data Stored Successfully !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddNewCall.this, CallHandlingActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddNewCall.this, "Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }

}