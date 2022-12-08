package com.example.ticktickui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ticktickui.Client.Models.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TeachersViewAdapter extends BaseAdapter {

    // Declare Variables

    Context c;
    LayoutInflater inflater;
    private List<Teacher> Teachers = null;
    private ArrayList<Teacher> arraylist;

    public TeachersViewAdapter(Context context, List<Teacher> teachers_list) {
        c = context;
        this.Teachers = teachers_list;
        inflater = LayoutInflater.from(c);
        this.arraylist = new ArrayList<Teacher>();
        this.arraylist.addAll(teachers_list);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return Teachers.size();
    }

    @Override
    public Teacher getItem(int position) {
        return Teachers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.teacher_cell, null);
            holder.name = (TextView) view.findViewById(R.id.t_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(Teachers.get(position).name);
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        Teachers.clear();
        if (charText.length() == 0) {
            Teachers.addAll(arraylist);
        } else {
            for (Teacher t : arraylist) {
                if (t.name.toLowerCase(Locale.getDefault()).contains(charText)) {
                    Teachers.add(t);
                }
            }
        }
        notifyDataSetChanged();
    }

}