package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class ReadReview extends AppCompatActivity {
    String bodyReview, dateReview;
    EditText date,messageBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_review);
        date = findViewById(R.id.date);
        messageBody = findViewById(R.id.messageBody);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("bodyReview") != null)
            bodyReview = bundle.getString("bodyReview");

        if (bundle.getString("dateReview") != null)
            dateReview = bundle.getString("dateReview");

        date.setText(dateReview);
        messageBody.setText(bodyReview);
    }
}