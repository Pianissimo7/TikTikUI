package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import client.TiktikJavaHttpClient.ClientAndroid;
import cz.msebera.android.httpclient.Header;

import com.example.ticktickui.global_variables.GlobalVariables;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.nio.charset.StandardCharsets;

public class HomeStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);

        TextView t_name = (TextView)findViewById(R.id.t_name);
        t_name.setText(GlobalVariables.name);
    }

}