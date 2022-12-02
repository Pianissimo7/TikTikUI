package com.example.ticktickui;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import java.util.regex.*;
import java.util.*;

import com.example.ticktickui.global_variables.GlobalVariables;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // inflate the view. make all the objects on the screen visible
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // get all the inputs
        TextView input_name = (TextView) view.findViewById(R.id.et_name);
        TextView input_phone = (TextView) view.findViewById(R.id.ep_phone);
        TextView input_email = (TextView) view.findViewById(R.id.tea_email);
        TextView input_password = (TextView) view.findViewById(R.id.et_password);
        TextView input_re_password = (TextView) view.findViewById(R.id.et_repassword);
        Switch input_type = (Switch) view.findViewById(R.id.switch1);

        // button functionality
        Button btn_Register = (Button) view.findViewById(R.id.btn_register);
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get all the values from the input fields
                String name = input_name.getText().toString();
                String phone = input_phone.getText().toString();
                String email = input_email.getText().toString();
                String password = input_password.getText().toString();
                String re_password = input_re_password.getText().toString();
                boolean type = input_type.isChecked();

                // set the global variables
                GlobalVariables.email = email;
                GlobalVariables.name = name;
                GlobalVariables.phone = phone;
                GlobalVariables.type = type;

                if (verify_register(name, phone, email, password, re_password)) {
                    // choose what type of homepage to go to
                    if (!type) {
                        switch_to_home_student_activity();
                    } else {
                        switch_to_home_teacher_activity();
                    }
                }
            }
        });

        return view;
    }
    // switches to the student home page
    public void switch_to_home_student_activity() {
        Intent home_student_activity = new Intent(this.getActivity(), HomeStudentActivity.class);
        startActivity(home_student_activity);
    }
    // switches to the teacher home page
    public void switch_to_home_teacher_activity() {
        Intent home_teacher_activity = new Intent(this.getActivity(), HomeTeacherActivity.class);
        startActivity(home_teacher_activity);
    }
    private boolean verify_register(String name, String phone, String email, String password, String re_password) {
        if (name == "") {
            System.out.println("failed on name. name was left blank");
            return false;
        }
        if (phone.length() != 10) {
            System.out.println("failed on phone length, expected 10, got: " + phone.length());
            return false;
        }
        if (password == re_password) {
            System.out.println("failed on password re verification " + password + " != " + re_password);
            return false;
        }
        String regex = "^[\\p{L}0-9!#$%&'*+\\/=?^_`{|}~-][\\p{L}0-9.!#$%&'*+\\/=?^_`{|}~-]{0,63}@[\\p{L}0-9-]+(?:\\.[\\p{L}0-9-]{2,7})*$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(email).matches()) {
            System.out.println("failed on email verification");
            return false;
        }
        return true;
    }
}