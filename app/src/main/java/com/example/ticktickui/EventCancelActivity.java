package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticktickui.global_variables.GlobalVariables;

import java.time.LocalTime;
import java.util.function.Function;

public class EventCancelActivity extends AppCompatActivity {

    private TextView Name, Date, Time, Place;
    private LocalTime time;
    private Event event_to_remove;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_cancel);
        initWidgets();

        Bundle extra = getIntent().getExtras();
        time = LocalTime.of(extra.getInt("hour"), extra.getInt("minute"));

        event_to_remove = Event.eventForDateAndTime(CalendarUtils.selectedDate, time);

        Name.setText(GlobalVariables.name);
        Date.setText("Date: " + event_to_remove.getDate());
        Time.setText("Time: " + event_to_remove.getTime());
        Place.setText("at: " + event_to_remove.get_pick_up_place());

        Button btn_submit = (Button) findViewById(R.id.b_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Function<Integer, Integer> onSuccess = (t) ->
//                {
//                    DeleteEvent(v);
//                    finish();
//                    Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_LONG).show();
//                    return 0;
//                };
//                Function<Integer, Integer> onFailure = (t) ->
//                {
//                    finish();
//                    Toast.makeText(getBaseContext(), "Couldn't make your request", Toast.LENGTH_LONG).show();
//                    return 0;
//                };
//                GlobalVariables.client.DeleteLesson();
                DeleteEvent(v);
                finish();

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
        Event.eventsList.remove(event_to_remove);
    }
}