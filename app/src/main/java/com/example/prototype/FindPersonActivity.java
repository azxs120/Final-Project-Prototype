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
import android.widget.Toast;

import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FindPersonActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private String userPhoneNumber= null;
    private FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_person);
        db = FirebaseConnection.getFirebaseFirestore();
        //get the user email
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("key") != null)
            userPhoneNumber = bundle.getString("key");

        this.setTitle("Search Person");
        //Assign variable
        listView = findViewById(R.id.userList);
        //add item in array list
        //for (int i = 0; i <= 100; i++)
            //stringArrayList.add("Item" + i);
        db.collection("users")//go to users table
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {//אם הגישה לטבלה הצליחה
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {//תביא את כל הרשומות
                                String phoneFromDB = doc.getString("Mobile Number");
                                stringArrayList.add(phoneFromDB);
                                }

                            }
                        }
                });


        //Initialize adapter
        adapter = new ArrayAdapter<>(FindPersonActivity.this
                , android.R.layout.simple_list_item_1, stringArrayList);
        //Set adapter on list view
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Display click item position in toast
                Toast.makeText(getApplicationContext(), adapter.getItem(position), Toast.LENGTH_SHORT).show();
                String ID = adapter.getItem(position).toString();
                //take the id number to PersonInfoActivity
                Intent intent = new Intent(FindPersonActivity.this,PersonInfoActivity.class);
                intent.putExtra("key", userPhoneNumber);//take the email to CallHandlingActivity
                intent.putExtra("keyId",ID);
                startActivity(intent);
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Initialize menu inflater
        MenuInflater menuInflater = getMenuInflater();
        //Inflate menu
        menuInflater.inflate(R.menu.menu_search,menu);
        //Initialize menu item
        MenuItem menuItem = menu.findItem(R.id.search_view);
        //Initialize search view
        SearchView SearchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            public boolean onQueryTextChange(String newText){
                //Filter array list
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
