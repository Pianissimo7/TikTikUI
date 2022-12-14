package com.example.ticktickui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticktickui.Client.Models.Teacher;
import com.example.ticktickui.global_variables.GlobalVariables;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

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
        // sign to teacher button functionality
        Button btn_sign_to_teacher = (Button) view.findViewById(R.id.b_sign_to_teacher);
        btn_sign_to_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Function<Integer, Integer> onSuccess = (t) -> {
                    Toast.makeText(c, "Success!", Toast.LENGTH_LONG).show();
                    return_to_home();
                    return 0;
                };
                Function<Integer, Integer> onFailure = (t) -> {
                    Toast.makeText(c, "failed to sign teacher", Toast.LENGTH_LONG).show();
                    return -1;
                };
                GlobalVariables.client.ConnectStudentToTeacher(GlobalVariables.user_id, Teachers.get(position).id, onSuccess, onFailure);

            }
        });
        return view;
    }
    public void return_to_home() {
        Intent home_activity = new Intent(c, HomeStudentActivity.class);
        c.startActivity(home_activity);
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