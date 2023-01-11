package com.example.ticktickui;

import static androidx.core.content.ContextCompat.getDrawable;

import static com.example.ticktickui.CalendarUtils.selectedDate;

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

import com.example.ticktickui.Client.Models.Lesson;
import com.example.ticktickui.global_variables.GlobalVariables;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class HourAdapter extends ArrayAdapter<Lesson>
{
    Context c;
    public HourAdapter(@NonNull Context context, List<Lesson> lessons)
    {
        super(context, 0, lessons);
        c = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Lesson lesson = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hour_cell, parent, false);

        setHour(convertView, lesson.date.toLocalTime());
        setEvent(convertView, lesson);

        Button btn_set_lesson = convertView.findViewById(R.id.button);
            btn_set_lesson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if (lesson.Student_id == -1) {
                        if(!GlobalVariables.is_teacher) {
                            intent = new Intent(c, EventEditActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("student_id", GlobalVariables.user_id);
                            intent.putExtra("student_name", GlobalVariables.name);
                        }
                        else
                            intent = new Intent(c, TeacherSetLessonActivity.class);
                    }
                    else {
                        intent = new Intent(c, EventCancelActivity.class);
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("hour", lesson.date.toLocalTime().getHour());
                    intent.putExtra("minute", lesson.date.toLocalTime().getMinute());
                    c.startActivity(intent);
                }
            });

        return convertView;
    }

    private void setHour(View convertView, LocalTime time)
    {
        TextView timeTV = convertView.findViewById(R.id.timeTV);
        timeTV.setText(CalendarUtils.formattedShortTime(time));
    }

    private void setEvent(View view, Lesson lesson)
    {
        Button button =view.findViewById(R.id.button);
        button.setEnabled(true);

        if (lesson.Student_id != -1) {
            button.setText(R.string.lesson_set);
            if(!GlobalVariables.is_teacher) {
                if (GlobalVariables.user_id == lesson.Student_id) {
                    button.setBackground(getDrawable(c, R.drawable.btn_custom_lesson_set));
                }
                else {
                    button.setBackground(getDrawable(c, R.drawable.btn_custom_lesson_disabled));
                    button.setText(R.string.lesson_taken);
                    button.setEnabled(false);
                }
            }
            else
            {
                button.setBackground(getDrawable(c, R.drawable.btn_custom_lesson_set));
            }
            boolean invalid_set_lesson_time = (GlobalVariables.is_teacher) ? selectedDate.isBefore(LocalDate.now()) : selectedDate.isBefore(LocalDate.now().plusDays(1));
            if (invalid_set_lesson_time) {
                if (GlobalVariables.is_teacher || lesson.Student_id == GlobalVariables.user_id) {
                    button.setBackground(getDrawable(c, R.drawable.btn_custom_lesson_passed));
                    button.setText(R.string.lesson_passed);
                    button.setEnabled(false);
                }
            }

        }
        else {
            button.setText(R.string.order_lesson);
            button.setBackground(getDrawable(c, R.drawable.btn_custom));
        }
    }
}













