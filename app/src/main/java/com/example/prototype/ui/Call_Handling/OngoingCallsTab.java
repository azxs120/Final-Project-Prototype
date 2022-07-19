package com.example.prototype.ui.Call_Handling;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.prototype.DBClasess.Call;
import com.example.prototype.DBConnections.FirebaseConnection;
import com.example.prototype.DetailHistory;
import com.example.prototype.MyHistory;
import com.example.prototype.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OngoingCallsTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OngoingCallsTab extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER_MOBILE_NUMBER = "param1";
    private static final String USER_IDENTITY = "param2";

    private FirebaseFirestore db;
    ListView listView;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayList<Call> calls = new ArrayList<Call>();

    private String userMobileNumber;
    private String userIdentity;
    private String callBody, startDate, endDate, callSubject, homeOwnerCallStatus, tenantCallStatus;


    public OngoingCallsTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userMobileNumber Parameter 1.
     * @param userIdentity Parameter 2.
     * @return A new instance of fragment OngoingCallsTab.
     */
    // TODO: Rename and change types and number of parameters
    public static OngoingCallsTab newInstance(String userMobileNumber, String userIdentity) {
        OngoingCallsTab fragment = new OngoingCallsTab();
        Bundle args = new Bundle();
        args.putString(USER_MOBILE_NUMBER, userMobileNumber);
        args.putString(USER_IDENTITY, userIdentity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userMobileNumber = getArguments().getString(USER_MOBILE_NUMBER);
            userIdentity = getArguments().getString(USER_IDENTITY);
        }

        //listView = findViewById(R.id.userList);
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

                                if (tenantMobileNumber != null && homeOwnerMobileNumber != null)
                                    if (tenantMobileNumber.equals(userMobileNumber) ||
                                            homeOwnerMobileNumber.equals(userMobileNumber)) {
                                        callBody = doc.getString("Call Body");
                                        callSubject = doc.getString("Subject");
                                        homeOwnerCallStatus = doc.getString("Home owner Call Status");
                                        tenantCallStatus = doc.getString("Tenant Call Status");
                                        startDate = doc.getString("Start Date");
                                        endDate = doc.getString("End Date");

                                        Call newCall = new Call(callSubject, callBody, homeOwnerCallStatus, tenantCallStatus, startDate, endDate);
                                        calls.add(newCall);

                                        stringArrayList.add(i + ". " +  startDate);// put it in the list(for UI)
                                        i++;
                                    }
                            }
                        }
                    }
                });

    }

    public ArrayList<Call> getCalls(){
        return calls;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ongoing_calls_tab, container, false);
    }

}