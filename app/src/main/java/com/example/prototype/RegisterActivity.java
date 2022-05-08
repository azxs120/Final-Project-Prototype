package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.bson.Document;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoDatabase;

public class RegisterActivity extends AppCompatActivity {
    final int PASSWORD_LEN = 6;
    final int ID = 9;

    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView textView;
    EditText password;
    EditText renterPassword;
    EditText email;
    EditText id;
    EditText firstName;
    EditText lastName;

    MongoDatabase mongoDatabase;
    MongoClient mongoClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final String appId = "application-1-sfnjp";
        this.setTitle("Register A New User");

        //init app
        Realm.init(this);
        //this will build a new app object
        App app = new App(new AppConfiguration.Builder(appId).build());

        radioGroup = findViewById(R.id.radioGroup);
        textView = findViewById(R.id.permission);
        Button signUpBtn = findViewById(R.id.signUp);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the id of the specific radio btn
                int radioId = radioGroup.getCheckedRadioButtonId();
                //get the btn
                radioButton = findViewById(radioId);


                //input validation
                if (validateFirstName(view))
                    if (validateLastName(view))
                        if (validateEmail(view))
                            if (validateId(view))
                                if (validatePassword(view)) {


                                    //login to table
                                    app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
                                        @Override
                                        public void onResult(App.Result<User> result) {
                                            if(result.isSuccess()){
                                                Log.v("User", "Logged In anonymously");
                                            }else
                                                Log.v("User", "Failed to Login");
                                        }
                                    });

                                    User user = app.currentUser();
                                    mongoClient = user.getMongoClient("mongodb-atlas");
                                    mongoDatabase = mongoClient.getDatabase("RentMe");//the cluster(project)

                                    Document doc = new Document();
                                    doc.append("Email", email.getText().toString());
                                    doc.append("Password", password.getText().toString());
                                    doc.append("ID", id.getText().toString());
                                    doc.append("Password", password.getText().toString());
                                    doc.append("First Name", firstName.getText().toString());
                                    doc.append("Last Name", lastName.getText().toString());

                                    //write to table.
                                    mongoDatabase.getCollection("Person").insertOne(doc).getAsync(result -> {
                                        if (result.isSuccess()) {
                                            Log.v("Data", "Data Inserted Successfully");
                                            Snackbar.make(view, "Data Inserted Successfully", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            //create a new activity
                                            textView.setText("User was created successfully.");
                                        } else
                                            Log.v("Data", "our Error " + result.getError().toString());
                                    });

                                    Runnable r = new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intentLoadNewActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intentLoadNewActivity);//start the new activity.
                                        }
                                    };
                                    Handler h =  new Handler();
                                    h.postDelayed(r, 5000);
                                }
                    else
                        textView.setText("User was NOT created successfully.");

            }
        });

    }

    /**
     * a method that checks if the first name have a character that is not a letter.
     *
     * @param view
     * @return true if the name have only letters.
     */
    public boolean validateFirstName(View view) {
        this.firstName = findViewById(R.id.enterFirstName);

        //cent be empty
        if (firstName.getText().toString().isEmpty()) {
            firstName.setError("first name cent be empty");
            return false;
        }

        //contains only letters.
        if (!firstName.getText().toString().matches("[a-zA-Z]+")) {
            firstName.setError("first name is invalid");
            return false;
        }

        //else
        return true;
    }

    /**
     * a method that checks if the last name have a character that is not a letter.
     *
     * @param view
     * @return true if the name have only letters.
     */
    public boolean validateLastName(View view) {
        lastName = findViewById(R.id.enterLastName);

        //cent be empty
        if (lastName.getText().toString().isEmpty()) {
            lastName.setError("last name cent be empty");
            return false;
        }

        //contains only letters.
        if (!lastName.getText().toString().matches("[a-zA-Z]+")) {
            lastName.setError("last name is invalid");
            return false;
        }

        //else
        return true;
    }

    /**
     * a method that checks if the id is long enough.
     *
     * @param view
     * @return returns true if id contains 9 numbers
     */
    public boolean validateId(View view) {
        id = findViewById(R.id.id);
        //is it long enough?
        if (this.id.getText().toString().length() != ID) {
            id.setError("id needs to be 9 characters long.");
            return false;
        }
        return true;
    }

    /**
     * a method that checks if the password is valid
     *
     * @param view
     * @return true is the password is at least 6 characters long and the passwords match
     */
    public boolean validatePassword(View view) {
        //get the input.
        password = findViewById(R.id.password);
        renterPassword = findViewById(R.id.renterPassword);

        //if the password is empty
        if (password.getText().toString().isEmpty()) {
            password.setError("password can not be empty.");
            return false;
        }
        //is the password long enough?
        else if (password.getText().toString().length() >= PASSWORD_LEN) {
            //das the passwords match?
            if (password.getText().toString().matches(renterPassword.getText().toString()))
                return true;
            else
                renterPassword.setError("passwords das not match");
        }
        else if(password.getText().toString().length() < PASSWORD_LEN)
            password.setError("password needs to be at least 6 characters long.");

        return false;
    }

    /**
     * a method that will validate the that the mail is valid.
     * a mail address is valid if it contains @ and . is a curtain order
     *
     * @param view
     * @return true if the mail is valid
     */
    public boolean validateEmail(View view) {
        email = findViewById(R.id.email);
        String emailInput = email.getText().toString();
        if (emailInput.isEmpty()) {
            email.setError("email can not be empty.");
            return false;
        }
        //checks if the email in valid->has @ and a .
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("email is invalid.");
            return false;
        }
        return true;
    }

    /**
     * will get the radio value and make a toast.
     *
     * @param view
     */
    public void checkBtn(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);
        Toast.makeText(this, "You Are A " + radioButton.getText() + ", Only the Relevant Options Will Be Available.", Toast.LENGTH_SHORT).show();
    }
}