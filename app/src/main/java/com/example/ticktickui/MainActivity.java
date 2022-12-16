package com.example.ticktickui;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ticktickui.Client.ClientAndroid;
import com.example.ticktickui.Client.Models.Teacher;
import com.example.ticktickui.global_variables.GlobalVariables;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    public LoginFragment loginFragment;
    public RegisterFragment registerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);

        AuthenticationPagerAdapter pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager());
        GlobalVariables.client = new ClientAndroid(this);
        // TODO Refactor this
        loginFragment = new LoginFragment(GlobalVariables.client);
        // TODO Refactor this
        registerFragment = new RegisterFragment(GlobalVariables.client);
        pagerAdapter.addFragmet(loginFragment);
        pagerAdapter.addFragmet(registerFragment);
        viewPager.setAdapter(pagerAdapter);

        boolean debug = false;
        if (debug) {
            boolean test_student = false;
            if (test_student) {
                GlobalVariables.is_teacher = false;
                GlobalVariables.teacher = new Teacher();
                GlobalVariables.teacher.id = 2;
                GlobalVariables.user_id = 1;
                Intent intent = new Intent(this, HomeStudentActivity.class);
                startActivity(intent);
            }
            else {
                GlobalVariables.is_teacher = true;
                GlobalVariables.user_id = 1;
                Intent intent = new Intent(this, HomeTeacherActivity.class);
                startActivity(intent);
            }
        }
    }

    static class AuthenticationPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        public AuthenticationPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragmet(Fragment fragment) {
            fragmentList.add(fragment);
        }

    }
}