package com.example.prototype;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    Button login;
    Button register;
    EditText pwd;
    TextView email;
    ProgressBar progress;
    boolean isConnect;

    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.email);
        pwd=findViewById(R.id.password);
        progress=findViewById(R.id.progress_bar);
        login = findViewById(R.id.loginBtn);
        register = findViewById(R.id.registerNowBtn);

        db= FirebaseFirestore.getInstance();
        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.loginBtn:
                if(email.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                }else if( pwd.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
                }
                db.collection("users")
                        .get()
                        .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot doc : task.getResult()){
                                        String a=doc.getString("Email");
                                        String b=doc.getString("Password");
                                        String a1=email.getText().toString().trim();
                                        String b1=pwd.getText().toString().trim();

                                        if(a.equals(a1) && b.equals(b1)) {
                                            Intent home = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(home);
                                            Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();

                                            //take the email to AddNewActivity
                                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                            intent.putExtra("key",a);
                                            startActivity(intent);
                                            isConnect = true;
                                            break;
                                        }

                                    }if (!isConnect)
                                        Toast.makeText(LoginActivity.this, "Cannot login,incorrect Email and Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            case R.id.registerNowBtn:
                Intent register_view=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(register_view);
                break;
        }
    }
}