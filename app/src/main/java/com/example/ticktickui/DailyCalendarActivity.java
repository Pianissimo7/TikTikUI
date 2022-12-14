package com.example.ticktickui;

import static com.example.ticktickui.CalendarUtils.selectedDate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ticktickui.Client.Models.Lesson;
import com.example.ticktickui.global_variables.GlobalVariables;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class DailyCalendarActivity extends AppCompatActivity
{
    private int lesson_length = 45;
    private TextView monthDayText;
    private TextView dayOfWeekTV;
    private ListView hourListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calendar);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        if(!GlobalVariables.is_teacher)
            GlobalVariables.client.GetLessonsByObj(this, GlobalVariables.teacher.id, true);
        else
        {
            GlobalVariables.client.GetLessonsByObj(this, GlobalVariables.user_id, true);
        }
    }

    private void initWidgets()
    {
        monthDayText = findViewById(R.id.monthDayText);
        dayOfWeekTV = findViewById(R.id.dayOfWeekTV);
        hourListView = findViewById(R.id.hourListView);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setDayView();
    }

    private void setDayView()
    {
        monthDayText.setText(CalendarUtils.monthDayFromDate(selectedDate));
        String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTV.setText(dayOfWeek);
        setHourAdapter();
    }

    private void setHourAdapter()
    {
        HourAdapter hourAdapter = new HourAdapter(getApplicationContext(), hourEventList());
        hourListView.setAdapter(hourAdapter);
    }

    private ArrayList<HourEvent> hourEventList()
    {
        ArrayList<HourEvent> list = new ArrayList<>();

        for(int minutes = 0; minutes / 60 < 24; minutes += lesson_length)
        {
            LocalTime time = LocalTime.of(minutes / 60, minutes % 60);
            Event event = Event.eventForDateAndTime(selectedDate, time);
            HourEvent hourEvent = new HourEvent(time, event);
            list.add(hourEvent);
        }

        return list;
    }

    public void previousDayAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1);
        setDayView();
    }

    public void nextDayAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
        setDayView();
    }
    public void setLessonList(ArrayList<Lesson> lesson_list) {
        Event event;
        for (Lesson lesson : lesson_list) {
            LocalDate date = lesson.date.toLocalDate();
            LocalTime time = lesson.date.toLocalTime();
            event = new Event(lesson.Comment, date, time);
            Event.eventsList.add(event);
        }
    }
}