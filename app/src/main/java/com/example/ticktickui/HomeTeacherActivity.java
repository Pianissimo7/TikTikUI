package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeTeacherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_teacher);

        // calendar button functionality
        Button btn_calendar = (Button)findViewById(R.id.b_calendar);
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_to_calendar_activity();
            }
        });

        // students list button functionality
        Button btn_students_list = (Button)findViewById(R.id.b_teachers_list);
        btn_students_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_to_student_list_activity();
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
}