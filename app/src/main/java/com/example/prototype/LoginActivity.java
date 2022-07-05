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
    private Button login;
    private Button register;
    private EditText pwd;
    private TextView email;
    private ProgressBar progress;
    private boolean isConnect;
    private FirebaseFirestore db;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        pwd = findViewById(R.id.password);
        progress = findViewById(R.id.progress_bar);
        login = findViewById(R.id.loginBtn);
        register = findViewById(R.id.registerNowBtn);

        db = FirebaseConnection.getFirebaseFirestore();
        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {

            //we pressed login
            case R.id.loginBtn:
                String typedEmail = email.getText().toString().trim();
                if (!TextUtils.isEmpty(typedEmail))
                    if (typedEmail.matches(emailPattern))
                        Toast.makeText(LoginActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                else if (pwd.getText().toString().equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter valid password", Toast.LENGTH_SHORT).show();

                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        String emailFromDB = doc.getString("Email");
                                        String passwordFromDB = doc.getString("Password");
                                        String typedEmail = email.getText().toString().trim();
                                        String typedPassword = pwd.getText().toString().trim();

                                        //the data(Email And password) match
                                        if (emailFromDB.equals(typedEmail) && passwordFromDB.equals(typedPassword)) {
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("key", emailFromDB);//take the email to AddNewActivity
                                            startActivity(intent);
                                            isConnect = true;
                                            break;
                                        }

                                    }
                                    if (!isConnect)
                                        Toast.makeText(LoginActivity.this, "Cannot login,incorrect Email and Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;//this belongs to the switch case

            //we pressed register
            case R.id.registerNowBtn:
                Intent register_view = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register_view);
                break;
        }
    }
}