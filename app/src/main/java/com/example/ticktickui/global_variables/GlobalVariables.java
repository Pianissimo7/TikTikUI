package com.example.ticktickui.global_variables;

import android.annotation.SuppressLint;

import com.example.ticktickui.Client.ClientAndroid;
import com.example.ticktickui.Client.Models.Teacher;

public class GlobalVariables {
    /** USER PARAMETERS*/
    // TODO Refactor these to user_x
    public static String name = "default name";
    public static String phone = "default phone";
    public static String email = "default email";
    public static int user_id = -1;
    // TODO change this type to isTeacher, this is isTeacher we checked
    public static boolean type = true;
    @SuppressLint("StaticFieldLeak")
    public static ClientAndroid client;

    public static Teacher teacher = null;
    public static void UpdateFields(String n , String p, String e, int u_id, boolean t)
    {
        name = n;
        phone = p;
        email = e;
        user_id = u_id;
        type = t;

    }
}
