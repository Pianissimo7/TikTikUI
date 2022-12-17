package com.example.ticktickui;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ticktickui.Client.Models.Teacher;

import static com.example.ticktickui.global_variables.GlobalVariables.*;

import java.util.ArrayList;
import java.util.function.Function;

public class TeachersListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    TeachersViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_list);

        if(!is_teacher)
        {
            Function<ArrayList<Object>, Integer> onSuccess = (teachers) -> {
                ArrayList<Teacher> teachs = new ArrayList<>();
                teachers.forEach(t -> teachs.add((Teacher)t));
                setAdapter(teachs);
                return 0;
            };
            Function<Integer, Integer> onFailure = (t) -> {
                Toast.makeText(this.getBaseContext(), "FUCK", Toast.LENGTH_LONG).show();
                return -1;
            };
            client.GetAll(new Teacher(), onSuccess, onFailure);
        }
        SearchView search_bar =  findViewById(R.id.sv_search_teacher);
        search_bar.setOnQueryTextListener(this);

    }
    public void setAdapter(ArrayList<Teacher> teachers)
    {
        ListView list = findViewById(R.id.lv_teachers_list);
        adapter = new TeachersViewAdapter(this, teachers);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return false;
    }

}