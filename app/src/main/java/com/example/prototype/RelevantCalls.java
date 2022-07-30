package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.prototype.DBClasess.Apartment;
import com.example.prototype.DBClasess.Call;

import java.util.ArrayList;

public class RelevantCalls extends AppCompatActivity {

    ArrayList<Call> callsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relevant_calls);

        callsList = (ArrayList<Call>) getIntent().getSerializableExtra("relevantCalls");



    }
}