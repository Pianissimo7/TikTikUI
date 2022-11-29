package com.example.ticktickui;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ticktickui.global_variables.GlobalVariables;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;

public class LoginFragment extends Fragment {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static String BASE_URL = "http://10.12.9.46:5231";

    public LoginFragment() {
        // Required empty public constructor
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
                if (v == btn_Login) {
                    String email = input_email.getText().toString();
                    String password = input_password.getText().toString();

                    GlobalVariables.email = email;

                    login(email, password);
                }
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

    private void login(String email, String password) {
    }
    // send a request to the server
    public void send_request(String type, String request, String[] args) {
        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // TODO Auto-generated method stub
                response_handler(request, new String(responseBody, StandardCharsets.UTF_8));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // TODO Auto-generated method stub
                System.out.println(statusCode);
            }

        };
        if (type == "get") {
            client.get(request_builder(request, args), handler);
        }
        if (type == "post") {
            client.post(request_builder(request, args), handler);
        }
    }
    public static String request_builder(String request_type, String[] args) {
        String request =  BASE_URL;
        switch (request_type) {
            case "login":
                request += "/Message/";
        }
        return request;
    }
    // handel the response that was returned from the server
    public void response_handler(String request, String response) {
        switch (request) {
            case "Student":
                switch_to_home_student_activity();
                break;
            case "login":
                try {
                    JSONObject jsonObject = new JSONObject(response);
                } catch (JSONException err){

                }
                // choose what type of homepage to go to
                /*if (type) {
                    switch_to_home_student_activity();
                } else {
                    switch_to_home_teacher_activity();
                }*/

        }
    }
}