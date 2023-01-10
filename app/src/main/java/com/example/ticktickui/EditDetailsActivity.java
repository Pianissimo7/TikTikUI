package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ticktickui.Client.Models.Lesson;
import com.example.ticktickui.Client.Models.Student;
import com.example.ticktickui.Client.Models.Teacher;
import com.example.ticktickui.global_variables.GlobalVariables;

import java.util.ArrayList;
import java.util.function.Function;

public class EditDetailsActivity extends AppCompatActivity {

    private EditText email;
    private EditText name;
    private EditText phone;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        initWidgets();
        fillFields();

        Button apply = (Button) findViewById(R.id.b_apply_changes);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void initWidgets()
    {
        email = findViewById(R.id.tea_email_edit);
        name = findViewById(R.id.et_name_edit);
        phone = findViewById(R.id.ep_phone_edit);
        password = findViewById(R.id.et_password_edit);
    }
    private void fillFields() {
        email.setText(GlobalVariables.email);
        name.setText(GlobalVariables.name);
        phone.setText(GlobalVariables.phone);
    }
    private Object get_updated_user() {

        String s_email = email.getText().toString();
        String s_name = name.getText().toString();
        String s_phone = phone.getText().toString();
        String s_password = password.getText().toString();

        Teacher teacher = GlobalVariables.is_teacher ? new Teacher(s_name, s_email, s_phone, s_password) : null;
        Student student = GlobalVariables.is_teacher ?  null : new Student(s_name, s_email, s_phone, s_password);

        return GlobalVariables.is_teacher ? teacher : student;
    }
    private void submit_details() {
        Function<Integer, Integer> onSuccess = (t) ->
        {
            Toast.makeText(getBaseContext(), "Changes Successfully Applied", Toast.LENGTH_LONG).show();
            return 0;
        };
        Function<Integer, Integer> onFailure = (t) ->
        {
            Toast.makeText(getBaseContext(), "Couldn't apply changes", Toast.LENGTH_LONG).show();
            return 0;
        };
        GlobalVariables.client.UpdateDetails(GlobalVariables.user_id, GlobalVariables.is_teacher, get_updated_user(), onSuccess, onFailure);
    }
}