package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.ticktickui.Client.Models.Teacher;

import java.util.ArrayList;
import java.util.Arrays;

public class TeachersListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    TeachersViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_list);

        Teacher[] temp_teachers = new Teacher[5];

        for (int i = 0; i < 5 ; i++) {
            temp_teachers[i] = new Teacher("test_teacher " + i, "", "", "");
        }

        ListView list = (ListView) findViewById(R.id.lv_teachers_list);

        ArrayList<Teacher> arraylist = new ArrayList<Teacher>(Arrays.asList(temp_teachers));

        // Pass results to ListViewAdapter Class
        adapter = new TeachersViewAdapter(this, arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        SearchView search_bar = (SearchView) findViewById(R.id.sv_search_teacher);
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