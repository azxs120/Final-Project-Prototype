package com.example.prototype;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.prototype.DBClasess.Apartment;
import com.example.prototype.DBClasess.Call;

import java.util.ArrayList;

public class RelevantCalls extends AppCompatActivity {
    EditText subject, messageBody, startDate, endDate, homeOwnerPhoneNumber;
    ArrayList<Call> callsList = new ArrayList<>();
    ListView listView;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relevant_calls);
        this.setTitle("Apartment Call History");
        listView = findViewById(R.id.apartmentsList);

        callsList = (ArrayList<Call>) getIntent().getSerializableExtra("relevantCalls");

        for (int i = 0; i < callsList.size(); i++) {
            stringArrayList.add(callsList.get(i).getStartDate());
        }

        //Initialize adapter
        adapter = new ArrayAdapter<>(RelevantCalls.this
                , android.R.layout.simple_list_item_1, stringArrayList);
        //Set adapter on list view
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Display click item position in toast
                if (adapter != null) {
                    String pickedItem = adapter.getItem(position).toString();

                    for (int i = 0; i < callsList.size(); i++) {
                        if(callsList.get(i).getStartDate().equals(pickedItem)){
                            Intent intent = new Intent(RelevantCalls.this, RelevantApartmentCallDetails.class);
                            intent.putExtra("relevantCalls", callsList);//take the email to CallHandlingActivity
                            intent.putExtra("index", i);//take the email to CallHandlingActivity
                            startActivity(intent);
                        }
                    }
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