package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class ViewHistoryActivity extends AppCompatActivity {
    EditText fromDate;
    EditText ToDate;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    DatePickerDialog.OnDateSetListener onDateSetListener2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        fromDate = findViewById(R.id.fromDate);
        ToDate = findViewById(R.id.ToDate);

        final Calendar calendarFrom = Calendar.getInstance();
        final int yearFrom = calendarFrom.get(Calendar.YEAR);
        final int monthFrom = calendarFrom.get(Calendar.MONTH);
        final int dayFrom = calendarFrom.get(Calendar.DAY_OF_MONTH);
        final Calendar calendarTo = Calendar.getInstance();
        final int yearTo = calendarTo.get(Calendar.YEAR);
        final int monthTo = calendarTo.get(Calendar.MONTH);
        final int dayTo = calendarTo.get(Calendar.DAY_OF_MONTH);

        fromDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ViewHistoryActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,yearFrom,monthFrom,dayFrom);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                }
            });
        ToDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ViewHistoryActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener2,yearTo,monthTo,dayTo);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth+"/"+month+"/"+year;
                fromDate.setText(date);

            }
        };
        onDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth+"/"+month+"/"+year;
                ToDate.setText(date);
            }
        };

        }
    }

