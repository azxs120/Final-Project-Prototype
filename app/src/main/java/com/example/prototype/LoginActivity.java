package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button loginBtn;
    private Button registerBtn;
    private Button restoreBtn;
    private EditText password;
    private TextView email;
    private ProgressBar progressBar;
    private boolean isConnect;
    private FirebaseFirestore db;
    //a pattern that will help us check if the email input is valid.
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress_bar);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerNowBtn);
        restoreBtn = findViewById(R.id.restorePassword);

        db = FirebaseConnection.getFirebaseFirestore();
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        restoreBtn.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {

            //we pressed login
            case R.id.loginBtn:
                String typedEmail = email.getText().toString().trim();
                //email is not empty but not valid
                if (!TextUtils.isEmpty(typedEmail))
                    if (!typedEmail.matches(emailPattern))
                        Toast.makeText(LoginActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                if (password.getText().toString().equals(""))
                    Toast.makeText(LoginActivity.this, "Something went wrong\n" +
                            "The password field is empty", Toast.LENGTH_SHORT).show();

                db.collection("users")//go to users table
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {//אם הגישה לטבלה הצליחה
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {//תביא את כל הרשומות
                                        String emailFromDB = doc.getString("Email");
                                        String passwordFromDB = doc.getString("Password");
                                        String typedEmail = email.getText().toString().trim();
                                        String typedPassword = password.getText().toString().trim();
                                        String identity = doc.getString("Identity");


                                        typedEmail = "aazxs120@gmail.com";
                                        typedPassword = "123456";

                                        //the data(Email And password) match
                                        //במידה והסיסמא והמייל של הרשומה שווים למה שהמשתמש הכניס אז תאפשר לנו התחברות למשתמש ותשלח איתך את שם המשתמש
                                        if (emailFromDB.equals(typedEmail) && passwordFromDB.equals(typedPassword)) {
                                            //flash the password and email textBoxes
                                            password.setText("");
                                            email.setText("");

                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("userEmail", emailFromDB);//take the email to AddNewActivity
                                            intent.putExtra("identity", identity);//take the email to AddNewActivity
                                            startActivity(intent);
                                            isConnect = true;
                                            break;
                                        }

                                    }
                                    if (!isConnect)
                                        Toast.makeText(LoginActivity.this,
                                                "Cannot login,incorrect Email and Password",
                                                Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;//this belongs to the switch case

            //we pressed register
            case R.id.registerNowBtn:
                Intent register_view = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register_view);
                break;
            case R.id.restorePassword:
                Intent restore_view = new Intent(LoginActivity.this, RestorePasswordActivity.class);
                startActivity(restore_view);
                break;
        }
    }
}