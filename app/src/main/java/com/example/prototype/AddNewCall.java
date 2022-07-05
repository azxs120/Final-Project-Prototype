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
    private EditText title;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    Button newCallBtn;
    EditText message;
    String txtTitle, txtMessage, txtCurrentDate;
    Calendar calendar = Calendar.getInstance();
    private String userEmail = null;
    private String mobileNumber = null;
    private String identity = null;
    private String otherIdentity = null;
    private RadioButton tenant, homeOwner;
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_call);
        this.setTitle("Open New Call");
        title = (EditText) findViewById(R.id.subject);
        message = (EditText) findViewById(R.id.messageBody);
        newCallBtn = (Button) findViewById(R.id.submitCall);
        radioGroup = findViewById(R.id.radioGroup);
        tenant = findViewById(R.id.tenant);
        homeOwner = findViewById(R.id.homeOwner);

        //get the user email
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("key") != null)
            userEmail = bundle.getString("key");

        // Initialize Firebase Auth
        //mAuth = FirebaseConnection.getFirebaseAuth();
        mAuth =FirebaseAuth.getInstance();
        // Initialize Firebase Firestore
        //db = FirebaseConnection.getFirebaseFirestore();

        db = FirebaseFirestore.getInstance();

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
        newCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTitle = title.getText().toString().trim();
                txtMessage = message.getText().toString().trim();
                txtCurrentDate = DateFormat.getDateInstance().format(calendar.getTime());

                //get the MobileNumber of the user
                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    //run on the rows of the table
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        String emailFromDB = doc.getString("Email");//get the email of every user
                                        //the emails match
                                        if (userEmail.equals(emailFromDB)) {
                                            mobileNumber = doc.getString("MobileNumber");//get the MobileNumber.
                                            identity = doc.getString("Identity");//get the identity
                                            Toast.makeText(AddNewCall.this, mobileNumber +"  " + identity , Toast.LENGTH_SHORT).show();

                                            if (identity.equals("tenantAndHomeOwner"))
                                                radioGroup.setVisibility(View.VISIBLE);//show me my options
                                            break;//done searching
                                        }
                                    }
                                }
                            }
                        });

                Map<String, Object> call = new HashMap<>();
                call.put("Subject", txtTitle);
                call.put("CallBody", txtMessage);
                call.put("Date", txtCurrentDate);
                call.put("Email", userEmail);
                call.put("Tenant Call Status", "open");
                call.put("Home owner Call Status", "open");
                //my identity
                if(identity.equals("tenant"))
                    call.put("tenant MobileNumber", mobileNumber);
                else if (identity.equals("homeOwner"))
                    call.put("homeOwner MobileNumber", mobileNumber);
                else {//tenantAndHomeOwner
                    getOtherIdentity();
                    call.put(identity + " MobileNumber", mobileNumber);
                }

                //other side identity
                if(identity.equals("tenant"))
                    call.put("homeOwner MobileNumber", null);
                else if (identity.equals("homeOwner"))
                    call.put("tenant MobileNumber", null);

                //TODO check if the connection is made?
                //TODO get the other side phone number after wi will create the connection table
                call.put(otherIdentity + " MobileNumber", null);

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
                            }
                        });
            }
        });


    }

    private void getOtherIdentity() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override//change the radioGroup
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tenant:
                        identity ="tenant";
                        break;
                    case R.id.homeOwner:
                        identity ="homeOwner";
                        break;
                }
            }
        });
    }

}