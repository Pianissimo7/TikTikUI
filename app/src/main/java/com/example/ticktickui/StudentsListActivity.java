package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ticktickui.Client.Models.Student;
import com.example.ticktickui.Client.Models.Teacher;
import com.example.ticktickui.global_variables.GlobalVariables;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class StudentsListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    StudentsViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

//        Student[] temp_students = new Student[5];

//        for (int i = 0; i < 5 ; i++) {
//            temp_students[i] = new Student("test_student " + i, "", "", "");
//        }
//
//        ListView list = (ListView) findViewById(R.id.lv_students_list);
//
//        ArrayList<Student> arraylist = new ArrayList<Student>(Arrays.asList(temp_students));


        Function<ArrayList<Student>, Integer> onSuccess = (students) ->
        {
            setAdapter(students);
            return 0;
        };
        Function<Integer, Integer> onFailure = (t) ->
        {
            Toast.makeText(this.getBaseContext(), "Couldn't connect", Toast.LENGTH_LONG).show();
            return 0;
        };
        GlobalVariables.client.GetStudentsByTeacher(GlobalVariables.user_id, onSuccess,onFailure);
        SearchView search_bar = (SearchView) findViewById(R.id.sv_search_student);
        search_bar.setOnQueryTextListener(this);

    }
    public void setAdapter(ArrayList<Student> students)
    {
        ListView list = (ListView) findViewById(R.id.lv_students_list);
        adapter = new StudentsViewAdapter(this, students, LocalTime.now());
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