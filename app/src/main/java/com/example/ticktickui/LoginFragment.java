package com.example.ticktickui;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ticktickui.Client.ClientAndroid;
import com.example.ticktickui.global_variables.GlobalVariables;

public class LoginFragment extends Fragment {

    ClientAndroid client;
    public LoginFragment(ClientAndroid client) {
        // Required empty public constructor
        this.client = client;
    }

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
    public void notApproved()
    {
        System.out.println("fuck");
    }
    public void login(String email, String password) {
        // TODO check email and password for correctness
        // TODO send a message to the server, ON RESPONSE UPDATE THE VIEW
        client.LoginUser(email, password);
    }
}