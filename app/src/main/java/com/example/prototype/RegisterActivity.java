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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    TextView txtSignIn;
    EditText edtFullName, edtEmail, edtMobile, edtPassword, edtConfirmPassword;
    ProgressBar progressBar;
    Button btnSignUp;
    String txtFullName, txtEmail, txtMobile, txtPassword, txtConfirmPassword,txtIdentity;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    RadioGroup radioGroup;
    RadioButton tenant, homeOwner,tenantAndHomeOwner;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

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
           @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tenant:
                        Toast.makeText(RegisterActivity.this, "U will sign up as a tenant", Toast.LENGTH_SHORT).show();
                        txtIdentity ="tenant";
                        break;
                    case R.id.homeOwner:
                        Toast.makeText(RegisterActivity.this, "U will sign up as a home owner", Toast.LENGTH_SHORT).show();
                        txtIdentity ="homeOwner";
                        break;
                    case R.id.tenantAndHomeOwner:
                        Toast.makeText(RegisterActivity.this, "U will sign up as a home owner and a tenant", Toast.LENGTH_SHORT).show();
                        txtIdentity ="tenantAndHomeOwner";
                        break;
                }
            }
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //txtIdentity = edtIdentity.getText().toString;
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

    private void SignUpUser() {
        progressBar.setVisibility(View.VISIBLE);
        btnSignUp.setVisibility(View.INVISIBLE);

        mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Map<String, Object> user = new HashMap<>();
                user.put("FullName", txtFullName);
                user.put("Email", txtEmail);
                user.put("MobileNumber", txtMobile);
                user.put("Password",txtPassword);
                user.put("identity",txtIdentity);

                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(RegisterActivity.this, "Data Stored Successfully !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this, "Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

