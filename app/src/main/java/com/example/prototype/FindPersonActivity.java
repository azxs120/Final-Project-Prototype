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
import android.widget.Toast;

import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindPersonActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private String userEmail = null;
    private FirebaseFirestore db;
    private Button showResultsBtn;
    private String userIdentity = null, identity = null;
    Map<String, String> personHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_person);
        this.setTitle("Search Person");

        db = FirebaseConnection.getFirebaseFirestore();
        //get the user email
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("userEmail") != null)
            userEmail = bundle.getString("userEmail");
        bundle = getIntent().getExtras();
        if (bundle.getString("identity") != null)
            identity = bundle.getString("identity");

        //Assign variable
        listView = findViewById(R.id.userList);

        //Assign variable
        listView = findViewById(R.id.userList);
        db.collection("users")//go to users table
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {//אם הגישה לטבלה הצליחה
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {//תביא את כל הרשומות
                                String phoneFromDB = doc.getString("Mobile Number");//get all phone numbers
                                String otherUserName = doc.getString("Name");
                                if (!(userEmail.equals(doc.getString("Email"))) && !(identity.equals(doc.getString("Identity"))))
                                    stringArrayList.add(phoneFromDB);

                                personHashMap.put(phoneFromDB, otherUserName);
                            }
                        }
                    }
                });

        showResultsBtn = findViewById(R.id.showResults);
        showResultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResultsBtn.setVisibility(View.INVISIBLE);

                //Initialize adapter
                adapter = new ArrayAdapter<>(FindPersonActivity.this
                        , android.R.layout.simple_list_item_1, stringArrayList);
                //Set adapter on list view
                listView.setAdapter(adapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Display click item position in toast
                        String otherUserPhoneNumber = adapter.getItem(position).toString();

                        //take the DATA number to PersonInfoActivity
                        Intent intent = new Intent(FindPersonActivity.this, PersonInfoActivity.class);
                        intent.putExtra("userEmail", userEmail);//take the email to PersonInfoActivity
                        intent.putExtra("otherUserPhoneNumber", otherUserPhoneNumber);
                        intent.putExtra("otherUserName", personHashMap.get(otherUserPhoneNumber).toString());
                        startActivity(intent);
                    }

                });
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
