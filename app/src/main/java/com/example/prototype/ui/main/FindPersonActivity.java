package com.example.prototype.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prototype.MainActivity;
import com.example.prototype.R;
import com.example.prototype.databinding.ActivityMainBinding;

import java.util.ArrayList;

import kotlin.LateinitKt;

public class FindPersonActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> stringArrayList= new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_person);
        this.setTitle("מצא");

        listView = findViewById(R.id.searchView);
        //add item in array list
        for (int i=0; i<=100; i++){
            stringArrayList.add("Item" +i);
        }
        //Imitialize adapter
        adapter = new ArrayAdapter<>(FindPersonActivity.this
                ,android.R.layout.simple_list_item_1,stringArrayList);
        //Set adapter on list view
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int postion, long id) {
                //Display click item position in toast
                Toast.makeText(getApplicationContext(), adapter.getItem(position),Toast.LENGTH_SHORT).show();
            }

        });
    }
}

