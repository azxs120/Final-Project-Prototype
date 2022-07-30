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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.prototype.DBClasess.Apartment;
import com.example.prototype.DBClasess.Call;
import com.example.prototype.DBConnections.FirebaseConnection;
import com.example.prototype.DetailHistory;
import com.example.prototype.R;
import com.example.prototype.ViewHistoryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowApartmentAfterSearch extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;

    private ArrayList<Apartment> relevantApartments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_apartment_after_search);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("relevantApartments") != null)
            relevantApartments =  (ArrayList<Apartment>)getIntent().getSerializableExtra("relevantApartments");

        //relevantApartments = bundle.getString("relevantApartments");

        listView = findViewById(R.id.userList);



    }
/*
    private void showRelevantStatus(int i) {
        switch (status) {
            case "open":
                if (calls.get(i - 1).getHomeOwnerCallStatus().equals("open") && calls.get(i - 1).getHomeOwnerCallStatus().equals("open")) {
                    stringArrayList.add(i + ". " + startDate);// put it in the list(for UI)
                    calls.get(i - 1).showInSearch(true);
                }

                break;
            case "handling":
                if (calls.get(i - 1).getHomeOwnerCallStatus().equals("handling") || calls.get(i - 1).getHomeOwnerCallStatus().equals("handling")) {
                    stringArrayList.add(i + ". " + startDate);// put it in the list(for UI)
                    calls.get(i - 1).showInSearch(true);
                }
                break;
            case "closed":
                if (calls.get(i - 1).getHomeOwnerCallStatus().equals("closed") && calls.get(i - 1).getHomeOwnerCallStatus().equals("closed")) {
                    stringArrayList.add(i + ". " + startDate);// put it in the list(for UI)
                    calls.get(i - 1).showInSearch(true);
                }
                break;
            case "noInput":
                stringArrayList.add(i + ". " + startDate);// put it in the list(for UI)
                calls.get(i - 1).showInSearch(true);
                break;
        }
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
    */

}