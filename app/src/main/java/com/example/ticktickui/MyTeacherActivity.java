package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ticktickui.global_variables.GlobalVariables;

import org.w3c.dom.Text;

public class MyTeacherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_teacher);

        TextView textView = (TextView) findViewById(R.id.tv_teacher_details);

        if (GlobalVariables.teacher != null) {
            String name = GlobalVariables.teacher.name;
            String email = GlobalVariables.teacher.email;
            String phone = GlobalVariables.teacher.phone;
            String details = name + "\nemail: " + email + "\nphone: " + phone;
            textView.setText(details);
        }
    }
}