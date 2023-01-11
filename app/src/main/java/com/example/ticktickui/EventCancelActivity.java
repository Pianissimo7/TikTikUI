package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticktickui.Client.Models.Lesson;
import com.example.ticktickui.global_variables.GlobalVariables;

import java.time.LocalTime;
import java.util.Optional;
import java.util.function.Function;

public class EventCancelActivity extends AppCompatActivity {

    private TextView Name, Date, Time, Place;
    private LocalTime time;
    private Lesson lesson_to_remove;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_cancel);
        initWidgets();

        Bundle extra = getIntent().getExtras();
        time = LocalTime.of(extra.getInt("hour"), extra.getInt("minute"));

        Optional<Lesson> opt = DailyCalendarActivity.lessons.stream().filter((l) ->
                CalendarUtils.selectedDate.equals(l.date.toLocalDate()) &&
                time.equals(l.date.toLocalTime())).findFirst();
        opt.ifPresent(lesson -> lesson_to_remove = lesson);

        Name.setText(GlobalVariables.name);
        Date.setText(new String("Date: " + lesson_to_remove.date.toLocalDate()));
        Time.setText(new String("Time: " + lesson_to_remove.date.toLocalTime()));
        Place.setText(new String("at: " + lesson_to_remove.Comment));

        Button btn_submit = findViewById(R.id.b_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Function<Integer, Integer> onSuccess = (t) ->
                {
                    DeleteEvent(v);
                    Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_LONG).show();
                    finish();
                    return 0;
                };
                Function<Integer, Integer> onFailure = (t) ->
                {
                    Toast.makeText(getBaseContext(), "Couldn't make your request", Toast.LENGTH_LONG).show();
                    System.out.println(t);
                    finish();
                    return 0;
                };
                GlobalVariables.client.DeleteLesson(lesson_to_remove.id , onSuccess, onFailure);
            }
        });

    }

    private void initWidgets()
    {
        Name = findViewById(R.id.t_name);
        Date = findViewById(R.id.eventDateTV);
        Time = findViewById(R.id.eventTimeTV);
        Place = findViewById(R.id.t_place);
    }

    public void DeleteEvent(View view)
    {
        DailyCalendarActivity.lessons.remove(lesson_to_remove);
    }
}