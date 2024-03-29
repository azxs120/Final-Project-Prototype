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

import com.example.prototype.DBClasess.Review;
import com.example.prototype.DBConnections.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewReviewsDate extends AppCompatActivity {
    private String otherUserPhoneNumber = null;
    private FirebaseFirestore db;
    ListView listView;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayList<Review> reviewList = new ArrayList<Review>();
    ArrayAdapter<String> adapter;
    private Button showReviewBtn;
    private String bodyReview, dateReview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews_date);

                //get user info from CallHandlingActivity
                Bundle bundle = getIntent().getExtras();
                if (bundle.getString("otherUserPhoneNumber") != null)
                    otherUserPhoneNumber = bundle.getString("otherUserPhoneNumber");

                this.setTitle(otherUserPhoneNumber + " Review");

                listView = findViewById(R.id.userList);

                db = FirebaseConnection.getFirebaseFirestore();

                db.collection("opinion")//go to users table
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {//אם הגישה לטבלה הצליחה
                                if (task.isSuccessful()) {
                                    int i = 1;
                                    for (QueryDocumentSnapshot doc : task.getResult()) {//תביא את כל הרשומות
                                        String reviewAboutPhone = doc.getString("Review About");
                                        if (otherUserPhoneNumber != null) {
                                            if (reviewAboutPhone.equals(otherUserPhoneNumber)) {
                                                bodyReview = doc.getString("Body");
                                                dateReview = doc.getString("Date");


                                                Review newReview = new Review(bodyReview, dateReview);
                                                reviewList.add(newReview);


                                                stringArrayList.add(i + ". " + dateReview);// put it in the list(for UI)
                                                i++;
                                            }

                                        }
                                    }
                                }
                            }

                        });


                showReviewBtn = findViewById(R.id.showReviewBtn);
                showReviewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showReviewBtn.setVisibility(View.INVISIBLE);

                        //Initialize adapter
                        adapter = new ArrayAdapter<>(com.example.prototype.ViewReviewsDate.this
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
                                    Intent intent = new Intent(com.example.prototype.ViewReviewsDate.this, ReadReview.class);
                                    intent.putExtra("bodyReview", reviewList.get(index).getReview());//take the callBody to
                                    intent.putExtra("dateReview", reviewList.get(index).getDate());//take the callBody to

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



