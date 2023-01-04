package com.example.ticktickui;


import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.timepicker.TimeFormat;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;


public class SettingsTeacherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_teacher);
    }
    public void apply() {

        int[] start_time_enums = {
                R.id.start_time_sunday,
                R.id.start_time_monday,
                R.id.start_time_tuesday,
                R.id.start_time_wednesday,
                R.id.start_time_thursday,
                R.id.start_time_friday
        };
        int[] end_time_enums = {
                R.id.end_time_sunday,
                R.id.end_time_monday,
                R.id.end_time_tuesday,
                R.id.end_time_wednesday,
                R.id.end_time_thursday,
                R.id.end_time_friday
        };

        Date[] start_times = new Date[start_time_enums.length];
        Date[] end_times = new Date[end_time_enums.length];
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a");

        for (int i = 0 ; i < start_time_enums.length ; i++) {
            EditText et = findViewById(start_time_enums[i]);
            String string = et.getText().toString();
            try {
                start_times[i] = formatter.parse(string);
            } catch (ParseException e) {
                Toast.makeText(getBaseContext(), "failed to apply settings", Toast.LENGTH_LONG).show();
            }
        }
        for (int i = 0 ; i < end_time_enums.length ; i++) {
            EditText et = findViewById(end_time_enums[i]);
            String string = et.getText().toString();
            try {
                end_times[i] = formatter.parse(string);
            } catch (ParseException e) {
                Toast.makeText(getBaseContext(), "failed to apply settings", Toast.LENGTH_LONG).show();
            }
        }

        //TODO send start_times and end_times to the client
    }
}