package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticktickui.global_variables.GlobalVariables;

import java.util.function.Function;

public class MyTeacherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_teacher);

        TextView textView = findViewById(R.id.tv_teacher_details);

        Button remove_teacher = findViewById(R.id.b_remove_teacher);

        if (GlobalVariables.teacher != null) {
            remove_teacher.setVisibility(View.VISIBLE);
            String name = GlobalVariables.teacher.name;
            String email = GlobalVariables.teacher.email;
            String phone = GlobalVariables.teacher.phone;
            String details = name + "\nemail: " + email + "\nphone: " + phone;
            textView.setText(details);
        }
        else {
            remove_teacher.setVisibility(View.INVISIBLE);
        }
        remove_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnect_from_teacher();
            }
        });
    }
    private void disconnect_from_teacher() {
        Function<Integer, Integer> onSuccess = (teacher) ->
        {
            Toast.makeText(this, "Success!", Toast.LENGTH_LONG).show();
            return 0;
        };
        Function<Integer, Integer> onFailure = (t) ->
        {
            Toast.makeText(this, "Failed to disconnect from teacher", Toast.LENGTH_LONG).show();
            return 0;
        };
        GlobalVariables.client.DeleteConnection(GlobalVariables.user_id, onSuccess, onFailure);
    }
}