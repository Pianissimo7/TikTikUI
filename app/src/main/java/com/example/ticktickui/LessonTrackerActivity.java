package com.example.ticktickui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticktickui.global_variables.GlobalVariables;

import java.util.function.Function;

public class LessonTrackerActivity extends AppCompatActivity {

    private TextView lesson_tracker;
    private ProgressBar progress_bar;
    private final int min_lesson_amount_for_test = 28;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_tracker);

        initWidgets();
        update_tracker();
    }
    private void initWidgets() {
        lesson_tracker = findViewById(R.id.t_lesson_tracker);
        progress_bar = findViewById(R.id.progress_bar);
    }
    private void update_tracker() {
        Function<Integer, Integer> onSuccess = (t) ->
        {

            double percentage = t / (double)min_lesson_amount_for_test * 100;
            lesson_tracker.setText(String.valueOf((int)percentage) + "%");
            progress_bar.setProgress((int)percentage, true);
            return 0;
        };
        Function<Integer, Integer> onFailure = (t) ->
        {
            Toast.makeText(getBaseContext(), "Couldn't get lesson count " + t, Toast.LENGTH_LONG).show();
            return 0;
        };
        GlobalVariables.client.GetNumberOfLessons(GlobalVariables.user_id, onSuccess, onFailure);
    }
}