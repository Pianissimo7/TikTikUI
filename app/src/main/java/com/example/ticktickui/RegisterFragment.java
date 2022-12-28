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
import android.widget.Toast;

import java.util.function.Function;
import java.util.regex.*;

import com.example.ticktickui.Client.Models.Student;
import com.example.ticktickui.Client.Models.Teacher;
import com.example.ticktickui.global_variables.GlobalVariables;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // inflate the view. make all the objects on the screen visible
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // get all the inputs
        TextView input_name = view.findViewById(R.id.et_name);
        TextView input_phone =  view.findViewById(R.id.ep_phone);
        TextView input_email =  view.findViewById(R.id.tea_email);
        TextView input_password =  view.findViewById(R.id.et_password);
        TextView input_re_password =  view.findViewById(R.id.et_repassword);
        Switch input_type = view.findViewById(R.id.switch1);

        // button functionality
        Button btn_Register = view.findViewById(R.id.btn_register);
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
                GlobalVariables.is_teacher = type;

                register(name, phone, email, password, re_password, type);
            }
        });

        return view;
    }
    private void register(String name, String phone, String email, String password, String re_password, boolean isTeacher)
    {
        if(verify_register(name, phone,email,password,re_password))
        {
            Function<String, Integer> onFailure = (errorMSG) ->
            {
                Toast.makeText(this.getContext(), errorMSG, Toast.LENGTH_LONG).show();
                return 0;
            };
            if(isTeacher)
            {
                Teacher teach = new Teacher(name, email, phone, password);
                Function<Integer, Integer> onSuccess = (t)->
                {
                    Intent intent = new Intent(this.getActivity(), HomeTeacherActivity.class);
                    startActivity(intent);
                    return 0;
                };
                GlobalVariables.client.RegisterUser(teach, onSuccess, onFailure);
            }
            else
            {
                Student student = new Student(name, email, phone, password);
                Function<Integer, Integer> onSuccess = (t) ->
                {
                    Intent intent = new Intent(this.getActivity(), HomeStudentActivity.class);
                    startActivity(intent);
                    return 0;
                };
                GlobalVariables.client.RegisterUser(student, onSuccess, onFailure);
            }
        }
        else{
            System.out.println("FUCK");
        }
    }
    private boolean verify_register(String name, String phone, String email, String password, String re_password) {
        if (name.equals("")) {
            Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        if (phone.length() != 10) {
            Toast.makeText(getContext(), "Phone length must be 10 ", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password == re_password) {
            Toast.makeText(getContext(), "Passwords are identical", Toast.LENGTH_LONG).show();
            return false;
        }
        String regex = "^[\\p{L}0-9!#$%&'*+\\/=?^_`{|}~-][\\p{L}0-9.!#$%&'*+\\/=?^_`{|}~-]{0,63}@[\\p{L}0-9-]+(?:\\.[\\p{L}0-9-]{2,7})*$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(email).matches()) {
            Toast.makeText(getContext(), "Email is not in correct format", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}