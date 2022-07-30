package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.prototype.ApartmentSearch.ApartmentSearch;
import com.example.prototype.DBClasess.Apartment;
import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class ApartmentSearchActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView CityText, StreetText;
    private CheckBox airConditioning, parking, balcony, elevator, bars, disabledAccess, renovated, furnished, mamad, pets;
    private ArrayList<Apartment> apartmentsList = new ArrayList<>();
    private Button findBtn;
    private Apartment apartment;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_search);
        this.setTitle("Search Apartment");

        CityText = (EditText) findViewById(R.id.CityText);
        StreetText = (EditText) findViewById(R.id.StreetText);
        airConditioning = (CheckBox) findViewById(R.id.airConditioning);
        parking = (CheckBox) findViewById(R.id.parking);
        balcony = (CheckBox) findViewById(R.id.balcony);
        elevator = (CheckBox) findViewById(R.id.elevator);
        bars = (CheckBox) findViewById(R.id.bars);
        disabledAccess = (CheckBox) findViewById(R.id.disabledAcess);
        renovated = (CheckBox) findViewById(R.id.renovated);
        pets = (CheckBox) findViewById(R.id.pets);
        furnished = (CheckBox) findViewById(R.id.furnished);
        mamad = (CheckBox) findViewById(R.id.mamad);

        findBtn = findViewById(R.id.findBtn);
        findBtn.setOnClickListener(this);

        // Initialize Firebase Auth
        mAuth = FirebaseConnection.getFirebaseAuth();
        db = FirebaseConnection.getFirebaseFirestore();

        //get the all of the apartments
        db.collection("apartments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //run on the rows of the table
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                //get the data from the Data Base
                                String cityName = doc.getString("City").trim();//get the email of every user
                                String street = doc.getString("Street").trim();//get the email of every user
                                String airConditioning = doc.getString("Air Conditioning").trim();//get the email of every user
                                String parking = doc.getString("Parking").trim();//get the email of every user
                                String balcony = doc.getString("Balcony").trim();//get the email of every user
                                String elevator = doc.getString("Elevator").trim();//get the email of every user
                                String bars = doc.getString("Bars").trim();//get the email of every user
                                String disabledAccess = doc.getString("Disabled Acess").trim();//get the email of every user
                                String renovated = doc.getString("Renovated").trim();//get the email of every user
                                String furnished = doc.getString("Furnished").trim();//get the email of every user
                                String panicRoom = doc.getString("Panic Room").trim();//get the email of every user
                                String pets = doc.getString("Pets").trim();//get the email of every user
                                String homeOwnerMobileNumber = doc.getString("Home Owner Mobile Number").trim();//get the email of every user

                                //create an object
                                Apartment apartment = new Apartment(cityName, street, airConditioning, parking, balcony,
                                        elevator, bars, disabledAccess, renovated, furnished, panicRoom, pets,
                                        homeOwnerMobileNumber);

                                apartmentsList.add(apartment);
                            }
                        }
                    }
                });


    }

    public void onClick(View v) {
        //Toast.makeText(ApartmentSearchActivity.this, "its clicked\n" , Toast.LENGTH_SHORT).show();
        //setContentView(R.layout.activity_apartment_search);

        CityText = (EditText) findViewById(R.id.CityText);
        StreetText = (EditText) findViewById(R.id.StreetText);
        airConditioning = (CheckBox) findViewById(R.id.airConditioning);
        parking = (CheckBox) findViewById(R.id.parking);
        balcony = (CheckBox) findViewById(R.id.balcony);
        elevator = (CheckBox) findViewById(R.id.elevator);
        bars = (CheckBox) findViewById(R.id.bars);
        disabledAccess = (CheckBox) findViewById(R.id.disabledAcess);
        renovated = (CheckBox) findViewById(R.id.renovated);
        pets = (CheckBox) findViewById(R.id.pets);
        furnished = (CheckBox) findViewById(R.id.furnished);
        mamad = (CheckBox) findViewById(R.id.mamad);

        String cityName = CityText.getText().toString();
        String street = StreetText.getText().toString();
        String txtCity, txtStreet,txtAirConditioning, txtParking, txtBalcony, txtElevator;
        String  txtBars, txtDisabledAccess, txtRenovated , txtFurnished,txtPanicRoom,txtPets;

        if (airConditioning.isChecked())
            txtAirConditioning = "true";
        else
            txtAirConditioning = "false";
        if (parking.isChecked())
            txtParking = "true";
        else
            txtParking = "false";
        if (balcony.isChecked())
            txtBalcony = "true";
        else
            txtBalcony = "false";
        if (elevator.isChecked())
            txtElevator = "true";
        else
            txtElevator = "false";
        if (bars.isChecked())
            txtBars = "true";
        else
            txtBars = "false";
        if (disabledAccess.isChecked())
            txtDisabledAccess = "true";
        else
            txtDisabledAccess = "false";
        if (renovated.isChecked())
            txtRenovated = "true";
        else
            txtRenovated = "false";
        if (furnished.isChecked())
            txtFurnished = "true";
        else
            txtFurnished = "false";
        if (mamad.isChecked())
            txtPanicRoom = "true";
        else
            txtPanicRoom = "false";
        if (pets.isChecked())
            txtPets = "true";
        else
            txtPets = "false";

        //create apartment from user input
        apartment = new Apartment(CityText.getText().toString(), StreetText.getText().toString(), txtAirConditioning, txtParking
                , txtBalcony, txtElevator, txtBars, txtDisabledAccess,
                txtRenovated, txtFurnished, txtPanicRoom, txtPets,"" );

        //ApartmentSearch
        ApartmentSearch apartmentSearch = new ApartmentSearch(apartmentsList);

        ArrayList<Apartment> relevantApartments =  apartmentSearch.searchApartment(apartment);

        //show relevant Apartments in different screen
        Intent intentLoadNewActivity = new Intent(ApartmentSearchActivity.this, ShowApartmentAfterSearch.class);
        //intentLoadNewActivity.putParcelableArrayListExtra("relevantApartments", relevantApartments);

        intentLoadNewActivity.putExtra("relevantApartments", relevantApartments);//take the email to CallHandlingActivity
        startActivity(intentLoadNewActivity);
    }

}