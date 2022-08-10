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

import com.example.prototype.DBClasess.Call;
import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PublishApartment extends AppCompatActivity implements View.OnClickListener {
    private TextView CityText, StreetText;
    private CheckBox airConditioning, parking, balcony, elevator, bars, disabledAcess, renovated, furnished, mamad, pets;
    private Button PublishBtn;
    private String HomeOwnerMobilNumber;
    private String txtCity, txtStreet, txtAirConditioning, txtparking, txtbalcony, txtelevator;
    private String txtbars, txtdisabledAcess, txtrenovated, txtfurnished, txtmamad, txtpets;
    private static int apartmentId = 3;
    int maxId = 0;
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

        // Initialize Firebase Auth
        mAuth = FirebaseConnection.getFirebaseAuth();
        db = FirebaseConnection.getFirebaseFirestore();

        db.collection("apartments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {//אם הגישה לטבלה הצליחה
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot doc : task.getResult()) {//תביא את כל הרשומות
                                String apartmentId = doc.getString("Apartment Id");
                                if (Integer.parseInt(apartmentId) > maxId)
                                    maxId = Integer.parseInt(apartmentId);
                            }
                        }
                    }
                });


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
        PublishBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

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

        apartmentId = maxId + 1;

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
        apartment.put("Home Owner Mobile Number", HomeOwnerMobilNumber);
        apartment.put("Apartment Id", String.valueOf(apartmentId));

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


        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {//אם הגישה לטבלה הצליחה
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {//תביא את כל הרשומות
                                String HomeOwnerMobilNumberFromDB = doc.getString("Mobile Number");

                                if (HomeOwnerMobilNumberFromDB.equals(HomeOwnerMobilNumber)) {
                                    db.collection("users").document(doc.getId()).
                                            update("Apartment Id", String.valueOf(apartmentId));
                                }
                            }
                        }
                    }
                });
    }

}