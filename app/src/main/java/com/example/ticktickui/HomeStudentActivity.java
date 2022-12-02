package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        // calendar button functionality
        Button btn_Register = (Button)findViewById(R.id.b_calendar);
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_to_calendar_activity();
            }
        });
    }
    public void switch_to_calendar_activity() {
        Intent calendar_activity = new Intent(this, DailyCalendarActivity.class);
        startActivity(calendar_activity);
    }

}