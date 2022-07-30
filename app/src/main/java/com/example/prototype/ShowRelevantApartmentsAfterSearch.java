package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.prototype.DBClasess.Apartment;
import com.example.prototype.DBClasess.Call;
import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowRelevantApartmentsAfterSearch extends AppCompatActivity {
    ArrayList<Apartment> apartments;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayList<Call> callsList = new ArrayList<>();
    private FirebaseFirestore db;
    String callBody = null;
    String subject = null;
    String startDate = null;
    String endDate = null;
    String homeOwnerMobileNumber = null;
    String tenantMobileNumber = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_relevant_apartments_after_search);
        this.setTitle("Apartments");
        listView = findViewById(R.id.apartmentsList);


        apartments = (ArrayList<Apartment>) getIntent().getSerializableExtra("relevantApartments");

        for (int i = 0; i < apartments.size(); i++) {
            stringArrayList.add(apartments.get(i).getCityName() + ", " + apartments.get(i).getStreet());
        }

        //Initialize adapter
        adapter = new ArrayAdapter<>(ShowRelevantApartmentsAfterSearch.this
                , android.R.layout.simple_list_item_1, stringArrayList);
        //Set adapter on list view
        listView.setAdapter(adapter);

        db = FirebaseConnection.getFirebaseFirestore();


        db.collection("calls")//go to users table
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {//אם הגישה לטבלה הצליחה
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {//תביא את כל הרשומות
                                String apartmentId = doc.getString("Apartment Id");
                                if (apartmentId != null) {
                                    callBody = doc.getString("Call Body");
                                    subject = doc.getString("Subject");
                                    startDate = doc.getString("Start Date");
                                    endDate = doc.getString("End Date");
                                    homeOwnerMobileNumber = doc.getString("homeOwner Mobile Number");
                                    tenantMobileNumber = doc.getString("tenant Mobile Number");

                                    Call call = new Call(subject, callBody, startDate, endDate, homeOwnerMobileNumber);
                                    callsList.add(call);
                                }
                            }
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Display click item position in toast
                if (adapter != null) {
                    String textOfTheItem = adapter.getItem(position).toString();

                    Intent intent = new Intent(ShowRelevantApartmentsAfterSearch.this, RelevantCalls.class);
                    intent.putExtra("relevantCalls", callsList);//take the email to CallHandlingActivity
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Initialize menu inflater
        MenuInflater menuInflater = getMenuInflater();
        //Inflate menu
        menuInflater.inflate(R.menu.menu_search, menu);
        //Initialize menu item
        MenuItem menuItem = menu.findItem(R.id.search_view);
        //Initialize search view
        SearchView SearchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            public boolean onQueryTextChange(String newText) {
                //Filter array list
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}