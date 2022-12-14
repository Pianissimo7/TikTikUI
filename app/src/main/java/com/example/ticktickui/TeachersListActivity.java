package com.example.ticktickui;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.ticktickui.Client.Models.Teacher;
import static com.example.ticktickui.global_variables.GlobalVariables.*;

import java.util.ArrayList;
import java.util.Arrays;

public class TeachersListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    TeachersViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_list);

        if(!is_teacher) 
        {
//            client.GetTeacherByStudent(user_id, (1)->{}, (1)->{});
        }
        SearchView search_bar = (SearchView) findViewById(R.id.sv_search_teacher);
        search_bar.setOnQueryTextListener(this);

    }
    public void setAdapter(ArrayList<Teacher> teachers)
    {
        ListView list = (ListView) findViewById(R.id.lv_teachers_list);
        adapter = new TeachersViewAdapter(this, teachers);
        list.setAdapter(adapter);
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