package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticktickui.Client.Models.Teacher;
import com.example.ticktickui.global_variables.GlobalVariables;

import java.util.function.Function;

public class HomeStudentActivity extends AppCompatActivity {
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);

        c = this.getBaseContext();
        TextView t_name = findViewById(R.id.t_name);
        t_name.setText(GlobalVariables.name);


        // calendar button functionality
        Button btn_calendar = findViewById(R.id.b_calendar);
        btn_calendar.setEnabled(true);
        System.out.println(GlobalVariables.teacher);
        if(GlobalVariables.teacher == null)
        {
            btn_calendar.setEnabled(false);
        }
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_to_calendar_activity();
            }
        });

        // my teacher button functionality
        Button btn_my_teacher = findViewById(R.id.b_my_teacher);
        btn_my_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Function<Teacher, Integer> onSuccess = (teacher) ->
                {
                    GlobalVariables.teacher = teacher;
                    if(teacher != null)
                        switch_to_my_teacher_activity();
                    else
                        Toast.makeText(c, "Please register to teacher.", Toast.LENGTH_LONG).show();
                    return 0;
                };
                Function<Integer, Integer> onFailure = (t) ->
                {
                    Toast.makeText(c, "Failed to connect", Toast.LENGTH_LONG).show();
                    return 0;
                };
                GlobalVariables.client.GetTeacherByStudent(GlobalVariables.user_id,onSuccess, onFailure);
            }
        });
        // teachers list button functionality
        Button btn_teachers_list = findViewById(R.id.b_teachers_list);
        btn_teachers_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_to_teacher_list_activity();
            }
        });

        // teachers list button functionality
        Button btn_edit_details = findViewById(R.id.b_edit_details);
        btn_edit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_to_edit_details_activity();
            }
        });

        Button btn_lesson_tracker = findViewById(R.id.b_lesson_tracker);
        btn_lesson_tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_to_lesson_tracker_activity();
            }
        });
    }

    public void switch_to_calendar_activity() {
        Intent calendar_activity = new Intent(this, DailyCalendarActivity.class);
        startActivity(calendar_activity);
    }

    public void switch_to_my_teacher_activity() {
        Intent my_teacher_activity = new Intent(this, MyTeacherActivity.class);
        startActivity(my_teacher_activity);
    }

    public void switch_to_teacher_list_activity() {
        Intent teacher_list_activity = new Intent(this, TeachersListActivity.class);
        startActivity(teacher_list_activity);
    }

    public void switch_to_edit_details_activity() {
        Intent edit_details_activity = new Intent(this, EditDetailsActivity.class);
        startActivity(edit_details_activity);
    }

    public void switch_to_lesson_tracker_activity() {
        Intent lesson_tracker_activity = new Intent(this, LessonTrackerActivity.class);
        startActivity(lesson_tracker_activity);
    }
}
