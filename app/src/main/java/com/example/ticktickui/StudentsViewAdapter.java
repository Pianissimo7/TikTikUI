package com.example.ticktickui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ticktickui.Client.Models.Student;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StudentsViewAdapter extends BaseAdapter {

    // Declare Variables

    Context c;
    LayoutInflater inflater;
    private List<Student> Students;
    private ArrayList<Student> arraylist;
    private LocalTime time;

    public StudentsViewAdapter(Context context, List<Student> students_list, LocalTime time) {
        c = context;
        this.Students = students_list;
        inflater = LayoutInflater.from(c);
        this.arraylist = new ArrayList();
        this.arraylist.addAll(students_list);
        this.time = time;
    }

    public static class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return Students.size();
    }

    @Override
    public Student getItem(int position) {
        return Students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.student_cell, null);
            holder.name = view.findViewById(R.id.t_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(Students.get(position).name);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditPage(Students.get(position).id, Students.get(position).name);
            }
        });
        return view;
    }
    private void setEditPage(int student_id, String student_name)
    {
        Intent intent = new Intent(c, EventEditActivity.class);
        intent.putExtra("hour", time.getHour());
        intent.putExtra("minute", time.getMinute());
        intent.putExtra("student_id", student_id);
        intent.putExtra("student_name", student_name);
        c.startActivity(intent);
    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        Students.clear();
        if (charText.length() == 0) {
            Students.addAll(arraylist);
        } else {
            for (Student s : arraylist) {
                if (s.name.toLowerCase(Locale.getDefault()).contains(charText)) {
                    Students.add(s);
                }
            }
        }
        notifyDataSetChanged();
    }

}