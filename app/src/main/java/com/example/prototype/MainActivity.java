package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "Tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //wire up the button
        Button btn = findViewById(R.id.searchApartment);//get the button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"get me an apartment");
                Toast.makeText(getApplicationContext(), "i am momo", Toast.LENGTH_SHORT).show();
            }

        });


    }
    void makeChane(){}
}