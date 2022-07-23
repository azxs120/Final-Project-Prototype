package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

public class PublishApartment extends AppCompatActivity {
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
        setContentView(R.layout.activity_publish_apartment);
        this.setTitle("Publish Your Apartment");

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

        PublishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtCity = CityText.getText().toString().trim();
                txtStreet = StreetText.getText().toString().trim();

                if (airConditioning.isChecked())
                    txtAirConditioning = "true";
                else
                    txtAirConditioning = "false";
                if (parking.isChecked())
                    txtparking = "true";
                else
                    txtparking = "false";
                if (balcony.isChecked())
                    txtbalcony = "true";
                else
                    txtbalcony = "false";
                if (elevator.isChecked())
                    txtelevator = "true";
                else
                    txtelevator = "false";
                if (bars.isChecked())
                    txtbars = "true";
                else
                    txtbars = "false";
                if (disabledAcess.isChecked())
                    txtdisabledAcess = "true";
                else
                    txtdisabledAcess = "false";
                if (renovated.isChecked())
                    txtrenovated = "true";
                else
                    txtrenovated = "false";
                if (furnished.isChecked())
                    txtfurnished = "true";
                else
                    txtfurnished = "false";
                if (mamad.isChecked())
                    txtmamad = "true";
                else
                    txtmamad = "false";
                if (pets.isChecked())
                    txtpets = "true";
                else
                    txtpets = "false";

                Map<String, Object> apartment = new HashMap<>();
                apartment.put("City", txtCity);
                apartment.put("Street", txtStreet);
                apartment.put("Air Conditioning", txtAirConditioning);
                apartment.put("Parking", txtparking);
                apartment.put("Balcony", txtbalcony);
                apartment.put("Elevator", txtelevator);
                apartment.put("Bars", txtbars);
                apartment.put("Disabled Acess", txtdisabledAcess);
                apartment.put("Renovated", txtrenovated);
                apartment.put("Furnished", txtfurnished);
                apartment.put("Panic Room", txtmamad);
                apartment.put("Pets", txtpets);
                apartment.put("Home Owner Mobile Number",HomeOwnerMobilNumber);



                    db.collection("apartments")
                            .add(apartment)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    Toast.makeText(PublishApartment.this, "The apartment was successfully advertised!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {


                                    Toast.makeText(PublishApartment.this, "----- Error ----- the call was not created", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });

            }

        });


    }
}