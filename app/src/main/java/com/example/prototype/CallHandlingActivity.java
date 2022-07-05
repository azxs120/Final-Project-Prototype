package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.prototype.ui.main.SectionsPagerAdapter;
import com.example.prototype.databinding.ActivityCallHandlingBinding;

import io.realm.Realm;

public class CallHandlingActivity extends AppCompatActivity {
    private String userEmail = null;
    private ActivityCallHandlingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCallHandlingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get the user email
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("key") != null)
            userEmail = bundle.getString("key");

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        //get the button
        FloatingActionButton addNewCall = binding.addNewCallBtn;
        //set a Click Listener
        addNewCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //open the "AddNewCall" Activity.
                    Intent intentLoadNewActivity = new Intent(CallHandlingActivity.this, AddNewCall.class);
                    intentLoadNewActivity.putExtra("key", userEmail);//take the email to AddNewCall
                    startActivity(intentLoadNewActivity);
                }
        });


    }

}