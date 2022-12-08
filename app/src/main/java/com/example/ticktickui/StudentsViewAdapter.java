package com.example.ticktickui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ticktickui.Client.Models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StudentsViewAdapter extends BaseAdapter {

    // Declare Variables

    Context c;
    LayoutInflater inflater;
    private List<Student> Students = null;
    private ArrayList<Student> arraylist;

    public StudentsViewAdapter(Context context, List<Student> students_list) {
        c = context;
        this.Students = students_list;
        inflater = LayoutInflater.from(c);
        this.arraylist = new ArrayList<Student>();
        this.arraylist.addAll(students_list);
    }

    public class ViewHolder {
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
            holder.name = (TextView) view.findViewById(R.id.t_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(Students.get(position).name);
        return view;
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