package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class ApartmentSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_search);

        this.setTitle("Search Apartment");
    }
}