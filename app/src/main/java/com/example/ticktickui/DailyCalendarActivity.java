package com.example.ticktickui;

import static com.example.ticktickui.CalendarUtils.selectedDate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticktickui.Client.Models.Lesson;
import com.example.ticktickui.global_variables.GlobalVariables;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

public class DailyCalendarActivity extends AppCompatActivity
{
    private int lesson_length = 45;
    private TextView monthDayText;
    private TextView dayOfWeekTV;
    private ListView hourListView;
    public static ArrayList<Lesson> lessons;
    private static boolean refreshed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calendar);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        Function<ArrayList<Lesson>, Integer> onSuccess = (lesons) ->
        {
            lessons = lesons;
            if(!refreshed)
                this.recreate();
            refreshed = true;
            return 0;
        };
        Function<Integer, Integer> onFailure = (t) ->
        {
            Toast.makeText(getBaseContext(), "Couldn't make your request", Toast.LENGTH_LONG).show();
            return 0;
        };
        if(!GlobalVariables.is_teacher)
            GlobalVariables.client.GetLessonsByObj( GlobalVariables.teacher.id, true,
                    onSuccess, onFailure);
        else
        {
            GlobalVariables.client.GetLessonsByObj(GlobalVariables.user_id, true,
                    onSuccess, onFailure);
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
            HourEvent hourEvent;
            if(lessons != null)
            {
                Optional<Lesson> optional = lessons.stream().filter((l) -> l.date.toLocalDate().equals(selectedDate)
                        && l.date.toLocalTime().equals(time)).findFirst();
                Lesson lesson = null;
                if(optional.isPresent()) {
                    lesson = optional.get();
                    System.out.println("FUCKKKKKKKKCKKCKCKCKCKCKCKCKC " + lesson);
                }
                hourEvent = new HourEvent(time, lesson);
                list.add(hourEvent);
            }
            else {
                hourEvent = new HourEvent(time, null);
                list.add(hourEvent);
            }
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
}