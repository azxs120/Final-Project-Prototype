package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.options.FindOptions;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;

import com.example.prototype.DBClasess.Call;
import com.example.prototype.DBClasess.Person;
import com.example.prototype.DBConnections.MongoConnection;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;


public class AddNewCall extends AppCompatActivity {
    private EditText title;
    private final int TIME_INTERVAL_FOR_SNOOZE = 5000;
    private final int GO_TO_HOME_OWNER_EMAIL = 14;
    private final int GO_TO_END_OF_HOME_OWNER_EMAIL = 14;


    RadioGroup radioGroup;
    RadioButton radioButton = null;
    private Call newCall;


    MongoClient mongoClient;
    MongoCollection mongoCollection;
    MongoDatabase mongoDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button newCallBtn;
        EditText theText;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_call);
        this.setTitle("Add A New Call");

        radioGroup = findViewById(R.id.radioGroupForAddCall);
        title = (EditText) findViewById(R.id.subject);
        theText = (EditText) findViewById(R.id.messageBody);

        int duration = Toast.LENGTH_SHORT;


        newCallBtn = (Button) findViewById(R.id.submitCall);
        //set what happens when the user clicks on "Add Call"
        newCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = null;
                //get the user Email from the previous activity
                Bundle extras = getIntent().getExtras();//get all of the extra data
                if (extras != null)
                    userEmail = extras.getString("userEmail");//extract the specific data we need
                String finalUserEmail = userEmail;//put it in a final variable

/*

                //for the snooze
                createNotificationChannel();
                //Toast.makeText(AddNewCall.this, "Snooze Set!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddNewCall.this, Snooze.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddNewCall.this, 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);



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
*/

                //create the relevant Call obj


                if (checkBtn(view) == 0) {//im a Tenant
                    //get the person using the "userEmail" we will get the landlord email(and chack that its not a null)
                    //create a connection to the db
                    mongoDatabase = MongoConnection.getConnection();

                    MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Person");

                    //Find a Single Document from MDB Site
                    Document queryFilter = new Document("Email", userEmail);

                    Log.v("EXAMPLE", "successfully found a document: " + mongoCollection.findOne().toString());

                    mongoCollection.findOne(queryFilter, new FindOptions()).getAsync(task -> {
                        if (task.isSuccess()) {
                            Document result = task.get();
                            //Toast.makeText(getApplicationContext(), task.get().toString() , Toast.LENGTH_LONG).show();
                            Log.v("EXAMPLE", "successfully found a document: " + result );

                            /* TODO:extract the HomeOwner from the task

LandlordEmail
                             */

                            //getting the home owner email.
                            int start = task.get().toString().indexOf("LandlordEmail") + GO_TO_HOME_OWNER_EMAIL;
                            int end = task.get().toString().indexOf("identity") - GO_TO_END_OF_HOME_OWNER_EMAIL;
                            task.get().toString().substring(start, end);

                            Toast.makeText(getApplicationContext(), task.get().toString().substring(start, end) , Toast.LENGTH_LONG).show();

                            newCall = new Call(task.get().toString().substring(start, end), finalUserEmail, title.getText().toString(), theText.getText().toString());

                        } else {
                            Toast.makeText(getApplicationContext(), "NOT NOT NOT found", Toast.LENGTH_LONG).show();
                            Log.e("EXAMPLE", "failed to find document with: ", task.getError());
                        }
                    });

                } else {//im a Home Owner
                    //get the person using the "userEmail" we will get the Tenant email(and chack that its not a null)
                    newCall = new Call(userEmail, "", title.getText().toString(), theText.getText().toString());
                }

                MongoConnection.killConnection();
                mongoDatabase = MongoConnection.getConnection();

                //create the call in the DB

                Document doc = new Document();
                doc.append("ID", newCall.getId());
                doc.append("LandLordEmail", newCall.getLandLordEmail());
                doc.append("tenantEmail", newCall.getTenantEmail());
                doc.append("landLordMode", newCall.getLandLordMode());
                doc.append("tenantMode", newCall.getTenantMode());
                doc.append("commitDate", newCall.getCommiteDate());
                doc.append("endDate", newCall.getEndDate());
                doc.append("title", newCall.getTitle());
                doc.append("callText", newCall.getCallText());
                doc.append("file", newCall.getFile());




                mongoDatabase.getCollection("Calls").insertOne(doc);

                //write to table.
                mongoDatabase.getCollection("Calls").insertOne(doc).getAsync(result -> {
                    if (result.isSuccess()) {
                        Log.v("Data", "Data Inserted Successfully");
                        Snackbar.make(view, "Data Inserted Successfully", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //create a new activity
                    } else
                        Log.v("Data", "our Error " + result.getError().toString());
                });






                //put the call in the dataBase
                //Toast toast = Toast.makeText(getApplicationContext(), newCall.toString(), duration);
                //toast.show();

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
     * <p>
     * if its Tenant return 0, else return 1;
     *
     * @return 0 of 1
     */
    public int checkBtn(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);//will set the radio btn to what is checked in the radio group
        return radioButton.getText().toString().matches("Tenant") ? 0 : 1;
    }

}