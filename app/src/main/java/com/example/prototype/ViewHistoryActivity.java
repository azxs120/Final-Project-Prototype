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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewHistoryActivity extends AppCompatActivity {
    private String otherUserPhoneNumber = null;
    private FirebaseFirestore db;
    ListView listView;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private Button showResultsBtn;
    private String callBody;
    private String callSubject;
    private String homeOwnerCallStatus;
    private String tenantCallStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        this.setTitle(otherUserPhoneNumber + " history");

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("otherUserPhoneNumber") != null)
            otherUserPhoneNumber = bundle.getString("otherUserPhoneNumber");

        db = FirebaseConnection.getFirebaseFirestore();


        db.collection("calls")//go to users table
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {//אם הגישה לטבלה הצליחה
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {//תביא את כל הרשומות
                                String tenantMobileNumber = doc.getString("tenant Mobile Number");
                                String homeOwnerMobileNumber = doc.getString("homeOwner Mobile Number");

                                if (tenantMobileNumber.equals(otherUserPhoneNumber) ||
                                        homeOwnerMobileNumber.equals(otherUserPhoneNumber)) {
                                    //TODO get all of the call data
                                     callBody = doc.getString("Call Body");
                                     callSubject = doc.getString("Subject");
                                     homeOwnerCallStatus = doc.getString("Home owner Call Status");
                                     tenantCallStatus = doc.getString("Tenant Call Status");
                                     String startDate = doc.getString("Start Date");
                                    stringArrayList.add(startDate);
                                    //String homeOwnerMobileNumber = doc.getString("homeOwner Mobile Number");
                                    //String tenantMobileNumber = doc.getString("tenant Mobile Number");



                                }


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
                adapter = new ArrayAdapter<>(ViewHistoryActivity.this
                        , android.R.layout.simple_list_item_1, stringArrayList);
                //Set adapter on list view
                listView.setAdapter(adapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Display click item position in toast
                        String otherUserPhoneNumber = adapter.getItem(position).toString();

                        //take the DATA number to PersonInfoActivity
                        Intent intent = new Intent(ViewHistoryActivity.this, DetailHistory.class);
                        intent.putExtra("callBody",callBody);//take the callBody to
                        intent.putExtra("callSubject",callSubject);
                        intent.putExtra("homeOwnerCallStatus",homeOwnerCallStatus);
                        intent.putExtra("tenantCallStatus",tenantCallStatus);
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