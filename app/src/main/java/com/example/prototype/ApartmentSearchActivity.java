package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class ApartmentSearchActivity extends AppCompatActivity {
    private TextView CityText,StreetText;
    private CheckBox airConditioning, parking, balcony, elevator, bars, disabledAcess, renovated, furnished, mamad, pets;
    private Button PublishBtn;
    private String HomeOwnerMobilNumber;
    private String txtCity, txtStreet,txtAirConditioning, txtparking, txtbalcony, txtelevator;
    private String  txtbars, txtdisabledAcess, txtrenovated , txtfurnished,txtmamad,txtpets;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_search);

        this.setTitle("Search Apartment");
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("userMobilNumber") != null)
            HomeOwnerMobilNumber = bundle.getString("userMobilNumber");

        CityText = (EditText) findViewById(R.id.CityText);
        StreetText = (EditText) findViewById(R.id.StreetText);
        airConditioning = (CheckBox) findViewById(R.id.airConditioning);
        parking = (CheckBox) findViewById(R.id.parking);
        balcony = (CheckBox) findViewById(R.id.balcony);
        elevator = (CheckBox) findViewById(R.id.elevator);
        bars = (CheckBox) findViewById(R.id.bars);
        disabledAcess = (CheckBox) findViewById(R.id.disabledAcess);
        renovated = (CheckBox) findViewById(R.id.renovated);
        pets = (CheckBox) findViewById(R.id.pets);
        furnished = (CheckBox) findViewById(R.id.furnished);
        mamad = (CheckBox) findViewById(R.id.mamad);
        PublishBtn = (Button) findViewById(R.id.PublishBtn);

        // Initialize Firebase Auth
        mAuth = FirebaseConnection.getFirebaseAuth();
        db = FirebaseConnection.getFirebaseFirestore();
    }
}