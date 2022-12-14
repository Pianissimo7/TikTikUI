package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ticktickui.Client.Models.Teacher;
import com.example.ticktickui.global_variables.GlobalVariables;

public class HomeStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);

        TextView t_name = (TextView)findViewById(R.id.t_name);
        t_name.setText(GlobalVariables.name);

        // calendar button functionality
        Button btn_calendar = (Button)findViewById(R.id.b_calendar);
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_to_calendar_activity();
            }
        });
        // teachers list button functionality
        Button btn_teachers_list = (Button)findViewById(R.id.b_teachers_list);
        btn_teachers_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_to_teacher_list_activity();
            }
        });
    }
    public void switch_to_calendar_activity() {
        Intent calendar_activity = new Intent(this, DailyCalendarActivity.class);
        startActivity(calendar_activity);
    }
    public void switch_to_teacher_list_activity() {
        Intent student_list_activity = new Intent(this, StudentsListActivity.class);
        startActivity(student_list_activity);
    }

}