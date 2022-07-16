package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private TextView txtSignIn;
    private EditText edtFullName, edtEmail, edtMobile, edtPassword, edtConfirmPassword;
    private ProgressBar progressBar;
    private Button btnSignUp;
    private String txtFullName, txtEmail, txtMobile, txtPassword, txtConfirmPassword, txtIdentity = "tenant";
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private RadioGroup radioGroup;
    private RadioButton tenant, homeOwner, tenantAndHomeOwner;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtSignIn = findViewById(R.id.txtSignIn);
        edtFullName = findViewById(R.id.edtSignUpFullName);
        edtEmail = findViewById(R.id.email);
        edtMobile = findViewById(R.id.edtSignUpMobile);
        edtPassword = findViewById(R.id.password);
        edtConfirmPassword = findViewById(R.id.renterPassword);
        progressBar = findViewById(R.id.signUpProgressBar);
        btnSignUp = findViewById(R.id.signUp);
        radioGroup = findViewById(R.id.radioGroup);
        tenant = findViewById(R.id.tenant);
        homeOwner = findViewById(R.id.homeOwner);
        tenantAndHomeOwner = findViewById(R.id.tenantAndHomeOwner);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override//change the radioGroup
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tenant:
                        Toast.makeText(RegisterActivity.this, "U will sign up as a tenant", Toast.LENGTH_SHORT).show();
                        txtIdentity = "tenant";
                        break;
                    case R.id.homeOwner:
                        Toast.makeText(RegisterActivity.this, "U will sign up as a home owner", Toast.LENGTH_SHORT).show();
                        txtIdentity = "homeOwner";
                        break;
                    case R.id.tenantAndHomeOwner:
                        Toast.makeText(RegisterActivity.this, "U will sign up as a home owner and a tenant", Toast.LENGTH_SHORT).show();
                        txtIdentity = "tenantAndHomeOwner";
                        break;
                }
            }
        });

        mAuth = FirebaseConnection.getFirebaseAuth();
        db = FirebaseConnection.getFirebaseFirestore();

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            //on click take us to login screen
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //check input
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtFullName = edtFullName.getText().toString();
                txtEmail = edtEmail.getText().toString().trim();
                txtMobile = edtMobile.getText().toString().trim();
                txtPassword = edtPassword.getText().toString().trim();
                txtConfirmPassword = edtConfirmPassword.getText().toString().trim();

                if (!TextUtils.isEmpty(txtFullName)) {
                    if (!TextUtils.isEmpty(txtEmail)) {
                        if (txtEmail.matches(emailPattern)) {
                            if (!TextUtils.isEmpty(txtMobile)) {
                                if (txtMobile.length() == 10) {
                                    if (!TextUtils.isEmpty(txtPassword)) {
                                        if (!TextUtils.isEmpty(txtConfirmPassword)) {
                                            if (txtConfirmPassword.equals(txtPassword)) {
                                                SignUpUser();
                                            } else {
                                                edtConfirmPassword.setError("Confirm Password and Password should be same.");
                                            }
                                        } else {
                                            edtConfirmPassword.setError("Confirm Password Field can't be empty");
                                        }
                                    } else {
                                        edtPassword.setError("Password Field can't be empty");
                                    }
                                } else {
                                    edtMobile.setError("Enter a valid Mobile");
                                }
                            } else {
                                edtMobile.setError("Mobile Number Field can't be empty");
                            }
                        } else {
                            edtEmail.setError("Enter a valid Email Address");
                        }
                    } else {
                        edtEmail.setError("Email Field can't be empty");
                    }
                } else {
                    edtFullName.setError("Full Name Field can't be empty");
                }
            }
        });
    }

    /**
     * add the user to the database
     */
    private void SignUpUser() {
        //add the progressBar
        progressBar.setVisibility(View.VISIBLE);
        //make the BTN disappear
        btnSignUp.setVisibility(View.INVISIBLE);

        //check if the phone exists
        db.collection("users")//go to users table
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {//אם הגישה לטבלה הצליחה
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {//תביא את כל הרשומות
                                String mobileNumberFromDB = doc.getString("Mobile Number");
                                //check if the phone number exist in the system
                                if (mobileNumberFromDB.equals(txtMobile))
                                    flag = false;
                            }
                        }
                    }
                });
            if(flag == true)
                createUser();
            else
                Toast.makeText(RegisterActivity.this,
                        "-----Error ----- user was not added the phone number already exists in the system",
                        Toast.LENGTH_SHORT).show();

    }

    private void createUser() {
        mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                //create an map object for the DB
                Map<String, Object> user = new HashMap<>();
                user.put("Full Name", txtFullName);
                user.put("Email", txtEmail);
                user.put("Mobile Number", txtMobile);
                user.put("Password", txtPassword);
                user.put("Identity", txtIdentity);


                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(RegisterActivity.this, "Data Stored Successfully!", Toast.LENGTH_SHORT).show();
                                finish();//get back to login screen
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this, "-----Error ----- user was not added " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                btnSignUp.setVisibility(View.VISIBLE);
            }
        });

    }
}

