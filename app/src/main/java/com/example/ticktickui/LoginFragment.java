package com.example.ticktickui;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticktickui.Client.ClientAndroid;
import com.example.ticktickui.Client.Models.Student;
import com.example.ticktickui.Client.Models.Teacher;
import com.example.ticktickui.global_variables.GlobalVariables;

import java.util.function.Function;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // inflate the view. make all the objects on the screen visible
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // get all the inputs
        TextView input_email = (TextView) view.findViewById(R.id.tea_email);
        TextView input_password = (TextView) view.findViewById(R.id.et_password);

        // button functionality
        Button btn_Login = (Button) view.findViewById(R.id.btn_login);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = input_email.getText().toString();
                String password = input_password.getText().toString();

                GlobalVariables.email = email;

                login(email, password);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    // switches to the student home page
    public void switch_to_home_student_activity() {
        Intent home_activity = new Intent(this.getActivity(), HomeStudentActivity.class);
        startActivity(home_activity);
    }
    // switches to the teacher home page
    public void switch_to_home_teacher_activity() {
        Intent home_activity = new Intent(this.getActivity(), HomeTeacherActivity.class);
        startActivity(home_activity);
    }
    public void login(String email, String password) {
        Function<Object, Integer> onSuccess = (T) ->
        {
            if (T instanceof Teacher) {
                Teacher teacher = (Teacher) T;
                GlobalVariables.UpdateFields(
                        teacher.name,
                        teacher.phone,
                        teacher.email,
                        teacher.id,
                        true);
                switch_to_home_teacher_activity();
                return 0;
            }
            else if(T instanceof Student)
            {
                Student student = (Student) T;
                GlobalVariables.UpdateFields(
                        student.name,
                        student.phone,
                        student.email,
                        student.id,
                        false);
                switch_to_home_student_activity();
            }
            return 0;
        };
        Function<Integer, Integer> onFailure = (errorCode) ->
        {
            if(errorCode == 404)
                Toast.makeText(this.getContext(), "Email or password are not currect", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this.getContext(), "Something went wrong", Toast.LENGTH_LONG).show();

            return 0;
        };

        GlobalVariables.client.LoginUser(email, password, onSuccess, onFailure);
    }
}