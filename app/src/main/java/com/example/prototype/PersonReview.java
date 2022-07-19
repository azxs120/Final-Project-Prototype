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

import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PersonReview extends AppCompatActivity {
    ListView listView;
    private FirebaseFirestore db;
    ArrayList<String> stringArrayList = new ArrayList<>();
    Map<String, String> personHashMap = new HashMap<>();
    private String userEmail, identity, userMobileNumber;
    private Button showResultsBtn;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_review);


        //get the user email
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("userEmail") != null)
            userEmail = bundle.getString("userEmail");
        //get the user identity
        bundle = getIntent().getExtras();
        if(bundle.getString("identity") != null)
            identity = bundle.getString("identity");
        bundle = getIntent().getExtras();
        if (bundle.getString("userMobilNumber") != null)
            userMobileNumber = bundle.getString("userMobilNumber");

        db = FirebaseConnection.getFirebaseFirestore();

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

                                if (!(userEmail.equals(doc.getString("Email"))) && !(identity.equals(doc.getString("Identity"))) && !identity.equals("tenantAndHomeOwner"))
                                    stringArrayList.add(phoneFromDB);
                                else if (!(userEmail.equals(doc.getString("Email"))) && identity.equals("tenantAndHomeOwner"))
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
                adapter = new ArrayAdapter<>(PersonReview.this
                        , android.R.layout.simple_list_item_1, stringArrayList);
                //Set adapter on list view
                listView.setAdapter(adapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(identity.equals("tenantAndHomeOwner")) {
                            //Display click item position in toast
                            String otherUserPhoneNumber = adapter.getItem(position).toString();

                            //take the DATA number to PersonInfoActivity
                            Intent intent = new Intent(PersonReview.this, ChooseIdentityForReview.class);
                            intent.putExtra("otherUserPhoneNumber", otherUserPhoneNumber);
                            intent.putExtra("userMobileNumber", userMobileNumber);
                            intent.putExtra("identity", identity);
                            intent.putExtra("userEmail", userEmail);

                            startActivity(intent);
                        }else{
                            //Display click item position in toast
                            String otherUserPhoneNumber = adapter.getItem(position).toString();

                            //take the DATA number to PersonInfoActivity
                            Intent intent = new Intent(PersonReview.this, WriteReview.class);
                            intent.putExtra("otherUserPhoneNumber", otherUserPhoneNumber);
                            intent.putExtra("userMobileNumber", userMobileNumber);
                            intent.putExtra("identity", identity);
                            startActivity(intent);
                        }


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