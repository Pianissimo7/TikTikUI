package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ticktickui.Client.Models.Lesson;
import com.example.ticktickui.global_variables.GlobalVariables;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class EventEditActivity extends AppCompatActivity
{
    private EditText et_pick_up_place;
    private TextView Name, date, Time;
    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();

        Bundle extra = getIntent().getExtras();
        time = LocalTime.of(extra.getInt("hour"), extra.getInt("minute"));

        Name.setText(GlobalVariables.name);
        date.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        Time.setText("Time: " + CalendarUtils.formattedTime(time));


        Button btn_submit = (Button) findViewById(R.id.b_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEventAction(v);
                finish();
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
        // if teacher
        if(!GlobalVariables.type)
        {
            ZoneId defaultZoneId = ZoneId.systemDefault();
            //local date + atStartOfDay() + default time zone + toInstant() = Date
            Date date = Date.from(CalendarUtils.selectedDate.atStartOfDay(defaultZoneId).toInstant());
            String eventName = et_pick_up_place.getText().toString();
            Lesson lesson = new Lesson(GlobalVariables.teacher.id, GlobalVariables.user_id, date , eventName);
            GlobalVariables.client.RegisterLesson(this, lesson);
        }
        else{
            // TODO implement me
        }
    }
    public void setLesson()
    {
        String eventName = et_pick_up_place.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
    }
}