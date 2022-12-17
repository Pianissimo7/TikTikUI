package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ticktickui.Client.Models.Student;
import com.example.ticktickui.global_variables.GlobalVariables;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.function.Function;

public class TeacherSetLessonActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    StudentsViewAdapter adapter;
    LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_set_lesson);


        Function<ArrayList<Student>, Integer> onSuccess = (students) ->
        {
            setAdapter(students);
            return 0;
        };
        Function<Integer, Integer> onFailure = (i) ->
        {
            Toast.makeText(this.getBaseContext(), "FUCK", Toast.LENGTH_LONG).show();
            return 0;
        };
        GlobalVariables.client.GetStudentsByTeacher(GlobalVariables.user_id, onSuccess, onFailure);

        Bundle extra = getIntent().getExtras();
        time = LocalTime.of(extra.getInt("hour"), extra.getInt("minute"));
    }
    public void setAdapter(ArrayList<Student> students)
    {
        ListView list = findViewById(R.id.lv_students_list);
        adapter = new StudentsViewAdapter(this, students, time);
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