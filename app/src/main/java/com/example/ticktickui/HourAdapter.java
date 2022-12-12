package com.example.ticktickui;

import static androidx.core.content.ContextCompat.getDrawable;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ticktickui.Client.ClientAndroid;

import java.time.LocalTime;
import java.util.List;

public class HourAdapter extends ArrayAdapter<HourEvent>
{
    Context c;
    public HourAdapter(@NonNull Context context, List<HourEvent> hourEvents)
    {
        super(context, 0, hourEvents);
        c = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        HourEvent event = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hour_cell, parent, false);

        setHour(convertView, event.time);
        setEvent(convertView, event.event);

        Button btn_set_lesson = (Button) convertView.findViewById(R.id.button);
        btn_set_lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(event.event);
                if (event.event == null) {
                    Intent intent = new Intent(c, EventEditActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("hour", event.time.getHour());
                    intent.putExtra("minute", event.time.getMinute());
                    c.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(c, EventCancelActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("hour", event.time.getHour());
                    intent.putExtra("minute", event.time.getMinute());
                    c.startActivity(intent);
                }
            }
        });

        return convertView;
    }

    private void setHour(View convertView, LocalTime time)
    {
        TextView timeTV = convertView.findViewById(R.id.timeTV);
        timeTV.setText(CalendarUtils.formattedShortTime(time));
    }

    private void setEvent(View view, Event event)
    {
        Button button = (Button) view.findViewById(R.id.button);
        if (event != null) {
            button.setText(R.string.lesson_set);
            button.setBackground(getDrawable(c, R.drawable.btn_custom_lesson_set));
        }
        else {
            button.setText(R.string.order_lesson);
            button.setBackground(getDrawable(c, R.drawable.btn_custom));
        }
    }
}













