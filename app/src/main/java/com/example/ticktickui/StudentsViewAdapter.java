package com.example.ticktickui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticktickui.Client.Models.Student;
import com.example.ticktickui.Client.Models.Teacher;
import com.example.ticktickui.global_variables.GlobalVariables;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

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
                if (time == null) {
                    setStudentInfoPage(position);
                }
                else {
                    setEditPage(position);
                }
            }
        });
        return view;
    }
    private void setStudentInfoPage(int position) {

        Student s = Students.get(position);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                c);

        View promptsView = inflater.inflate(R.layout.activity_student_info, null);

        alertDialogBuilder.setView(promptsView);

        String student_info = "";
        student_info += "name: " + s.name + "\n";
        student_info += "email: " + s.email + "\n";
        student_info += "phone: " + s.phone;

        alertDialogBuilder.setMessage(student_info + "\n\n" + "Would you like \nto remove the student?");

        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("Remove Student",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                remove_student(s.id);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }
    private void remove_student(int id) {
        Function<Integer, Integer> onSuccess = (teacher) ->
        {
            Toast.makeText(c, "Success!", Toast.LENGTH_LONG).show();
            return 0;
        };
        Function<Integer, Integer> onFailure = (t) ->
        {
            Toast.makeText(c, "Failed to remove the student", Toast.LENGTH_LONG).show();
            return 0;
        };
        GlobalVariables.client.DeleteConnection(id, onSuccess, onFailure);
    }
    private void setEditPage(int position) {
        Intent intent = new Intent(c, EventEditActivity.class);
        intent.putExtra("hour", time.getHour());
        intent.putExtra("minute", time.getMinute());
        intent.putExtra("student_id", Students.get(position).id);
        intent.putExtra("student_name", Students.get(position).name);
        c.startActivity(intent);
    }
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