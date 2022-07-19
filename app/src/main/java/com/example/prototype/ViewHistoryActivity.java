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

import com.example.prototype.DBClasess.Call;
import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewHistoryActivity extends AppCompatActivity {
    private String otherUserPhoneNumber = null, status = "noInput";
    private FirebaseFirestore db;
    ListView listView;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayList<Call> calls = new ArrayList<Call>();

    ArrayAdapter<String> adapter;
    private Button showHistoryBtn;
    private String callBody, startDate, endDate, callSubject, homeOwnerCallStatus, tenantCallStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("otherUserPhoneNumber") != null)
            otherUserPhoneNumber = bundle.getString("otherUserPhoneNumber");
        bundle = getIntent().getExtras();
        if (bundle.getString("status") != null)
            status = bundle.getString("status");

        this.setTitle(otherUserPhoneNumber + " history");

        listView = findViewById(R.id.userList);

        db = FirebaseConnection.getFirebaseFirestore();

        db.collection("calls")//go to users table
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {//אם הגישה לטבלה הצליחה
                        if (task.isSuccessful()) {
                            int i = 1;
                            for (QueryDocumentSnapshot doc : task.getResult()) {//תביא את כל הרשומות
                                String tenantMobileNumber = doc.getString("tenant Mobile Number");
                                String homeOwnerMobileNumber = doc.getString("homeOwner Mobile Number");


                                if (tenantMobileNumber != null && homeOwnerMobileNumber != null) {
                                    if (tenantMobileNumber.equals(otherUserPhoneNumber) ||
                                            homeOwnerMobileNumber.equals(otherUserPhoneNumber)) {
                                        callBody = doc.getString("Call Body");
                                        callSubject = doc.getString("Subject");
                                        homeOwnerCallStatus = doc.getString("Home owner Call Status");
                                        tenantCallStatus = doc.getString("Tenant Call Status");
                                        startDate = doc.getString("Start Date");
                                        endDate = doc.getString("End Date");

                                        Call newCall = new Call(callSubject, callBody, homeOwnerCallStatus, tenantCallStatus, startDate, endDate);
                                        calls.add(newCall);

                                        showRelevantStatus(i);

                                        //stringArrayList.add(i + ". " + startDate);// put it in the list(for UI)
                                        i++;
                                    }

                                }
                            }
                        }
                    }
                });


        showHistoryBtn = findViewById(R.id.showHistory);
        showHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHistoryBtn.setVisibility(View.INVISIBLE);

                //Initialize adapter
                adapter = new ArrayAdapter<>(ViewHistoryActivity.this
                        , android.R.layout.simple_list_item_1, stringArrayList);
                //Set adapter on list view
                listView.setAdapter(adapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Display click item position in toast
                        if (adapter != null) {
                            String textOfTheItem = adapter.getItem(position).toString();
                            int index = Integer.parseInt(textOfTheItem.substring(0, 1)) - 1;

                            //take the DATA number to PersonInfoActivity
                            Intent intent = new Intent(ViewHistoryActivity.this, DetailHistory.class);
                            intent.putExtra("callBody", calls.get(index).getCallBody());//take the callBody to
                            intent.putExtra("callSubject", calls.get(index).getSubject());
                            intent.putExtra("homeOwnerCallStatus", calls.get(index).getHomeOwnerCallStatus());
                            intent.putExtra("tenantCallStatus", calls.get(index).getTenantCallStatus());
                            intent.putExtra("startDate", calls.get(index).getStartDate());
                            intent.putExtra("endDate", calls.get(index).getEndDate());


                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

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
}