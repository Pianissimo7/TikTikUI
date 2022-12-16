package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticktickui.Client.Models.Lesson;
import com.example.ticktickui.global_variables.GlobalVariables;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.function.Function;

public class EventEditActivity extends AppCompatActivity
{
    private EditText et_pick_up_place;
    private TextView Name, date, Time;
    private LocalTime time;
    private int student_id;
    private String student_name;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();

        Bundle extra = getIntent().getExtras();
        time = LocalTime.of(extra.getInt("hour"), extra.getInt("minute"));

        student_id = extra.getInt("student_id");
        student_name = extra.getString("student_name");

        Name.setText(student_name);
        date.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        Time.setText("Time: " + CalendarUtils.formattedTime(time));


        Button btn_submit = (Button) findViewById(R.id.b_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEventAction(v);
                if (GlobalVariables.is_teacher) {
                    switch_to_home_teacher_activity();
                }
                else {
                    switch_to_home_student_activity();
                }
            }
        });
    }

    private void initWidgets()
    {
        Name = findViewById(R.id.t_name);
        et_pick_up_place = findViewById(R.id.et_pick_up_place);
        date = findViewById(R.id.eventDateTV);
        Time = findViewById(R.id.eventTimeTV);
    }

    public void saveEventAction(View view)
    {
        LocalDateTime date = LocalDateTime.of(CalendarUtils.selectedDate, time);
        String eventName = et_pick_up_place.getText().toString();
        Lesson lesson;
        if(GlobalVariables.is_teacher)
        {
            lesson = new Lesson(GlobalVariables.user_id, student_id, date , eventName);
        }
        else {
            lesson = new Lesson(GlobalVariables.teacher.id, student_id, date, eventName);
        }
        GlobalVariables.client.RegisterLesson(this, lesson);
    }
    public void setLesson(Lesson lesson)
    {
        DailyCalendarActivity.lessons.add(lesson);
        // TODO refresh the page
    }
    public void switch_to_home_student_activity() {
        Intent home_activity = new Intent(this, HomeStudentActivity.class);
        startActivity(home_activity);
    }
    // switches to the teacher home page
    public void switch_to_home_teacher_activity() {
        Intent home_activity = new Intent(this, HomeTeacherActivity.class);
        startActivity(home_activity);
    }
}