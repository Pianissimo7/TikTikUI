package com.example.ticktickui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ticktickui.Client.Models.Schedule;
import com.example.ticktickui.global_variables.GlobalVariables;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.function.Function;


public class SettingsTeacherActivity extends AppCompatActivity {
    private LocalTime[] start_times;
    private LocalTime[] end_times;
    private int[] start_times_fields_ids = {
            R.id.start_time_sunday,
            R.id.start_time_monday,
            R.id.start_time_tuesday,
            R.id.start_time_wednesday,
            R.id.start_time_thursday,
            R.id.start_time_friday,
            R.id.start_time_saturday
    };
    private int[] end_times_fields_ids = {
            R.id.end_time_sunday,
            R.id.end_time_monday,
            R.id.end_time_tuesday,
            R.id.end_time_wednesday,
            R.id.end_time_thursday,
            R.id.end_time_friday,
            R.id.end_time_saturday
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_teacher);

        Button btn_apply = findViewById(R.id.b_apply_settings);
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply();
            }
        });
        Function<Schedule, Integer> onSuccess = (times) ->
        {
            setTimes(times.start_times, times.end_times);
            return 0;
        };
        Function<Integer, Integer> onFailure = (t) ->
        {
            Toast.makeText(this.getBaseContext(), "failed to get working hours", Toast.LENGTH_LONG).show();
            return 0;
        };
        GlobalVariables.client.GetTeacherWorkTimes(GlobalVariables.user_id, onSuccess, onFailure);
    }
    private void setTimes(LocalTime[] start_times, LocalTime[] end_times)
    {
        for(int i = 0; i< start_times_fields_ids.length; i++)
        {
            EditText text = (EditText)findViewById(start_times_fields_ids[i]);
            String text_string = start_times[i].toString();
            text.setText(text_string);
        }
        for(int i = 0; i< end_times_fields_ids.length; i++)
        {
            EditText text = (EditText)findViewById(end_times_fields_ids[i]);
            String text_string =  end_times[i].toString();
            text.setText(text_string);
        }
    }
    private LocalTime getTime(String to_parse)
    {
        String[] split = to_parse.split(":");
        return LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }
    public void apply() {
        Schedule schedule = new Schedule();
        for (int i = 0; i < start_times_fields_ids.length ; i++) {
            EditText et = findViewById(start_times_fields_ids[i]);
            String time_string = et.getText().toString();
            LocalTime time;
            try {
                time = getTime(time_string);
            }
            catch (Exception e) {
                Toast.makeText(getBaseContext(), "illegal time at: " + time_string, Toast.LENGTH_LONG).show();
                return;
            }
        }
        for (int i = 0; i < end_times_fields_ids.length ; i++) {
            EditText et = findViewById(end_times_fields_ids[i]);
            String time_string = et.getText().toString();
            LocalTime time;
            try {
                time = getTime(time_string);
            }
            catch (Exception e) {
                Toast.makeText(this.getBaseContext(), "illegal time at: " + time_string, Toast.LENGTH_LONG).show();
                return;
            }
            schedule.end_times[i] = time;
        }
        Function<Integer, Integer> onSuccess = (t) ->
        {
            Intent home_activity = new Intent(this.getBaseContext(), HomeTeacherActivity.class);
            startActivity(home_activity);
            Toast.makeText(this.getBaseContext(), "Success", Toast.LENGTH_LONG).show();
            return 0;
        };
        Function<Integer, Integer> onFailure = (t) ->
        {
            Toast.makeText(this.getBaseContext(), "Failure", Toast.LENGTH_LONG).show();
            return 0;
        };
        GlobalVariables.client.UpdateTeacherWorkTimes(GlobalVariables.user_id, schedule, onSuccess, onFailure);

    }
}