package com.example.ticktickui.global_variables;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.example.ticktickui.Client.ClientAndroid;
import com.example.ticktickui.Client.Models.Teacher;
import com.example.ticktickui.Client.SemiClient;

public class GlobalVariables {
    /** USER PARAMETERS*/
    public static String name = "default name";
    public static String phone = "default phone";
    public static String email = "default email";
    public static int user_id = -1;
    public static boolean is_teacher = true;
    @SuppressLint("StaticFieldLeak")
    public static ClientAndroid client;
    public static SemiClient semiClient = new SemiClient();
    public static Teacher teacher = null;

    public static void UpdateFields(String n , String p, String e, int u_id, boolean t)
    {
        name = n;
        phone = p;
        email = e;
        user_id = u_id;
        is_teacher = t;

    }
}
