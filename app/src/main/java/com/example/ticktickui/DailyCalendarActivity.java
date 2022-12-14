package com.example.ticktickui;

import static com.example.ticktickui.CalendarUtils.selectedDate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticktickui.Client.Models.Lesson;
import com.example.ticktickui.Client.Models.Schedule;
import com.example.ticktickui.Client.SemiClient;
import com.example.ticktickui.global_variables.GlobalVariables;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

public class DailyCalendarActivity extends AppCompatActivity
{
    private final int lesson_length = 45;
    private TextView monthDayText;
    private TextView dayOfWeekTV;
    private ListView hourListView;
    public static ArrayList<Lesson> lessons;
    private static boolean refreshed_lessons = false;
    private static boolean refreshed_times = false;
    private static LocalTime[] startTimes;
    private static LocalTime[] endTimes;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calendar);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        Function<ArrayList<Lesson>, Integer> onSuccess_lessons = (lesons) ->
        {
            this.lessons = lesons;
            refreshed_lessons = true;
            this.recreate();
            return 0;
        };
        Function<Integer, Integer> onFailure_lessons = (t) ->
        {
            Toast.makeText(getBaseContext(), "Couldn't get lessons", Toast.LENGTH_LONG).show();
            return 0;
        };
        Function<Schedule, Integer> onSuccess_times = (times) ->
        {
            startTimes = times.start_times;
            endTimes = times.end_times;
            refreshed_times = true;
            this.recreate();
            return 0;
        };
        Function<Integer, Integer> onFailure_times = (t) ->
        {
            Toast.makeText(getBaseContext(), "Couldn't get times", Toast.LENGTH_LONG).show();
            return 0;
        };
        int id = GlobalVariables.is_teacher ? GlobalVariables.user_id : GlobalVariables.teacher.id;
        if(!refreshed_lessons) {
            GlobalVariables.client.GetLessonsByObj(id, true,
                    onSuccess_lessons, onFailure_lessons);
        }
        if(!refreshed_times)
        {
            GlobalVariables.client.GetTeacherWorkTimes(id, onSuccess_times, onFailure_times);
        }
    }

    private void initWidgets()
    {
        monthDayText = findViewById(R.id.monthDayText);
        dayOfWeekTV = findViewById(R.id.dayOfWeekTV);
        hourListView = findViewById(R.id.hourListView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            refreshed_lessons = false;
            refreshed_times = false;
        }
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        if(refreshed_times && refreshed_lessons)
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
        HourAdapter hourAdapter = new HourAdapter(getApplicationContext(), LessonsList());
        hourListView.setAdapter(hourAdapter);
    }

    private ArrayList<Lesson> LessonsList() {
        ArrayList<Lesson> list = new ArrayList<>();
        int day = selectedDate.getDayOfWeek().getValue() % 7;
        boolean is_selected_date_before_today = selectedDate.isBefore(LocalDate.now());
        boolean is_selected_date_before_tomorrow = selectedDate.isBefore(LocalDate.now().plusDays(1));
        LocalTime start, end;
        if (GlobalVariables.is_teacher) {
            start = is_selected_date_before_today ? LocalTime.of(0, 0) : startTimes[day];
            end = is_selected_date_before_today ? LocalTime.of(23, 59) : endTimes[day];
        }
        else {
            start = is_selected_date_before_tomorrow ? LocalTime.of(0, 0) : startTimes[day];
            end = is_selected_date_before_tomorrow ? LocalTime.of(23, 59) : endTimes[day];
        }
        if((GlobalVariables.is_teacher) ? is_selected_date_before_today : is_selected_date_before_tomorrow) {
            for(Lesson lesson : lessons)
            {
                if(lesson.date.toLocalDate().isEqual(selectedDate)) {
                    if (GlobalVariables.is_teacher) {
                        list.add(lesson);
                    }
                    else if (lesson.Student_id == GlobalVariables.user_id) {
                        list.add(lesson);
                    }
                }
            }
        }
        else {
            for (int minutes = start.getMinute() + start.getHour() * 60; minutes < end.getMinute() + end.getHour() * 60; minutes += lesson_length) {
                LocalTime time = LocalTime.of(minutes / 60, minutes % 60);
                if (lessons != null) {
                    Optional<Lesson> optional = lessons.stream().filter((l) -> l.date.toLocalDate().equals(selectedDate)
                            && l.date.toLocalTime().equals(time)).findFirst();
                    Lesson lesson = null;
                    if (optional.isPresent()) {
                        lesson = optional.get();
                    }
                    if (lesson == null) {
                        lesson = new Lesson(-1, -1, LocalDateTime.of(selectedDate, time), "");
                    }
                    list.add(lesson);
                }
                else {
                    list.add(new Lesson(-1, -1, LocalDateTime.of(selectedDate, time), ""));
                }
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