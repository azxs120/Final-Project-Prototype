package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RestorePasswordActivity extends AppCompatActivity {
    EditText email;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);
        this.setTitle("Restore Password");

        textView = findViewById(R.id.permission);
        email = findViewById(R.id.email);
        Button sendPassword = findViewById(R.id.sendPassword);
        sendPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //input validation
                if (validateEmail(view)) {
                    //create a new activity
                    textView.setText("password was sent to the email successfully");

                    Intent mail = new Intent(Intent.ACTION_SEND);
                    mail.putExtra(Intent.EXTRA_EMAIL, new String[]{email.getText().toString()});
                    mail.putExtra(Intent.EXTRA_SUBJECT, "your password By RentMe");
                    mail.putExtra(Intent.EXTRA_TEXT, "put password hare");

                    mail.setType("message/rfc822");
                    startActivity(Intent.createChooser(mail,"Choose an Email client" ));


                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            Intent intentLoadNewActivity = new Intent(RestorePasswordActivity.this, LoginActivity.class);
                            startActivity(intentLoadNewActivity);//start the new activity.
                        }
                    };
                    Handler h = new Handler();
                    h.postDelayed(r, 5000);
                } else
                    textView.setText("password was NOT sent to the email successfully.");
            }
        });

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
}