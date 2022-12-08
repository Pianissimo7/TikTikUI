package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.ticktickui.Client.Models.Student;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentsListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    StudentsViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        Student[] temp_students = new Student[5];

        for (int i = 0; i < 5 ; i++) {
            temp_students[i] = new Student("test_student " + i, "", "", "");
        }

        ListView list = (ListView) findViewById(R.id.lv_students_list);

        ArrayList<Student> arraylist = new ArrayList<Student>(Arrays.asList(temp_students));

        // Pass results to ListViewAdapter Class
        adapter = new StudentsViewAdapter(this, arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        SearchView search_bar = (SearchView) findViewById(R.id.sv_search_student);
        search_bar.setOnQueryTextListener(this);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
}