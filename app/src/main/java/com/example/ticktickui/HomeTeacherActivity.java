package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ticktickui.global_variables.GlobalVariables;

public class HomeTeacherActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_teacher);

        TextView t_name = findViewById(R.id.t_name);
        t_name.setText(GlobalVariables.name);

        // calendar button functionality
        Button btn_calendar = findViewById(R.id.b_calendar);
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_to_calendar_activity();
            }
        });

        // students list button functionality
        Button btn_students_list = findViewById(R.id.b_students_list);
        btn_students_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_to_student_list_activity();
            }
        });

        // settings button functionality
        Button btn_settings = findViewById(R.id.b_settings);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_to_settings_activity();
            }
        });
        // settings button functionality
        Button btn_edit_details = findViewById(R.id.b_edit_details);
        btn_edit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_to_edit_details_activity();
            }
        });
    }
    public void switch_to_calendar_activity() {
        Intent calendar_activity = new Intent(this, DailyCalendarActivity.class);
        startActivity(calendar_activity);
    }
    public void switch_to_student_list_activity() {
        Intent student_list_activity = new Intent(this, StudentsListActivity.class);
        startActivity(student_list_activity);
    }
    public void switch_to_settings_activity() {
        Intent settings_teacher_activity = new Intent(this, SettingsTeacherActivity.class);
        startActivity(settings_teacher_activity);
    }
    public void switch_to_edit_details_activity() {
        Intent edit_details_activity = new Intent(this, EditDetailsActivity.class);
        startActivity(edit_details_activity);
    }
}