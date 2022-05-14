package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
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

import java.util.Calendar;
import java.util.Date;


public class AddNewCall extends AppCompatActivity {
    private EditText title;
    private final int TIME_INTERVAL_FOR_SNOOZE = 5000;
    RadioGroup radioGroup;
    RadioButton radioButton;
    private Call newCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button newCallBtn;
        EditText theText;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_call);
        this.setTitle("Add New Call");

        title = (EditText) findViewById(R.id.subject);
        theText = (EditText) findViewById(R.id.messageBody);

        int duration = Toast.LENGTH_SHORT;


        newCallBtn = (Button) findViewById(R.id.submitCall);
        //set what happens when the user clicks on "Add Call"
        newCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = null;

                //for the snooze
                createNotificationChannel();
                Toast.makeText(AddNewCall.this, "Snooze Set!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddNewCall.this, Snooze.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddNewCall.this, 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                //get the user Email from the previous activity
                Bundle extras = getIntent().getExtras();//get all of the extra data
                if(extras != null)
                    userEmail = extras.getString("userEmail");//extract the specific data we need
                String finalUserEmail = userEmail;//put it in a final variable

                //get the date for the snooze
                Date date = new Date();

                //get the current date
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, date.getHours());
                calendar.set(Calendar.MINUTE, date.getMinutes());
                calendar.set(Calendar.SECOND, 0);
                //set the alarm and the snooze
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), TIME_INTERVAL_FOR_SNOOZE, pendingIntent);
                //alarmManager.set(AlarmManager.RTC_WAKEUP, timeS + fiveSec, pendingIntent);

                //create the relevant Call obj
                if(checkBtn(view) == 0) {//im a Tenant
                    //get the person using the "userEmail" we will get the landlord email(and chack that its not a null)
                    newCall = new Call("123", userEmail, title.getText().toString(), theText.getText().toString());

                }
                else {//im a Home Owner
                    //get the person using the "userEmail" we will get the Tenant email(and chack that its not a null)
                    newCall = new Call(userEmail, "", title.getText().toString(), theText.getText().toString());
                }

                //create the call in the DB


                //put the call in the dataBase
                Toast toast = Toast.makeText(getApplicationContext(), newCall.toString(), duration);
                toast.show();

                // how to get the text from the textBox  mEdit.getText().toString()
                Intent intentLoadNewActivity = new Intent(AddNewCall.this, CallHandlingActivity.class);
                intentLoadNewActivity.putExtra("userEmail", finalUserEmail);
                startActivity(intentLoadNewActivity);
            }
        });
    }

    /**
     * a method for the snooze
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";
            String description = "channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService((NotificationManager.class));
            notificationManager.createNotificationChannel(channel);


        }
    }
    /**
     * will get the radio value
     *
     * if its Tenant return 0, else return 1;
     * @param view
     * @return 0 of 1
     */
    public int checkBtn(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);
        return radioButton.getText().toString().matches("Tenant")? 0:1;
        //Toast.makeText(this, "You Are A " + radioButton.getText() + ", Only the Relevant Options Will Be Available.", Toast.LENGTH_SHORT).show();
    }

}