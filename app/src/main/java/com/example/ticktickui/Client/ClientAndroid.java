package com.example.ticktickui.Client;

import android.content.Context;

import com.example.ticktickui.Client.Models.Lesson;
import com.example.ticktickui.Client.Models.Teacher;
import com.google.gson.JsonObject;
import com.loopj.android.http.*;

import com.example.ticktickui.Client.Models.Student;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.entity.StringEntity;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ClientAndroid implements ClientInterface{
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static String BASE_URL = "http://10.12.9.46:5231"; // Remmember to update IP if changed...
    private Context context;

    /**
     * To set Context, please use getBaseContext()
     * @param context
     */
    public ClientAndroid(Context context)
    {
        this.context = context;
        client.addHeader("Content-Type", "application/json");

    }

    /**
     * this is called when a user is logged in,
     * This is used to update the server that a user connected,
     * to make sure we can see the actual logged in users.
     * @param Email
     * @param Password
     */
    @Override
    public void LoginUser(String Email, String Password)
    {
        JsonObject jdata = new JsonObject();
        /**
         * example :
         * jdata.addProperty("Email", "mosheCohen@gmail.com");
         *         jdata.addProperty("Password", "123456");
         */
        jdata.addProperty("Email", Email);
        jdata.addProperty("Password", Password);
        client.addHeader("Content-Type", "application/json");
        try {
            StringEntity entity = new StringEntity(jdata.toString());
            client.post(this.context, BASE_URL + "/Message/Login", entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    System.out.println(new String(bytes, StandardCharsets.UTF_8));
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    System.out.println(new String(bytes, StandardCharsets.UTF_8));
                }
            });
        }
        catch (Exception e)
        {

        }
    }

    /**
     * This is used to update the server that a user disconnected,
     * to make sure we can see the actual logged in users.
     * @param Email
     * @param Password
     */
    @Override
    public void DisconnectUser(String Email, String Password) {
        System.out.println("Disconnect user");

        JsonObject jdata = new JsonObject();
        /**
         * example :
         * jdata.addProperty("Email", "mosheCohen@gmail.com");
         *         jdata.addProperty("Password", "123456");
         */
        jdata.addProperty("Email", Email);
        jdata.addProperty("Password", Password);
        try {
            StringEntity entity = new StringEntity(jdata.toString());
            client.post(this.context, BASE_URL + "/Message/Disconnect", entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    System.out.println("Success");
//                    System.out.println(new String(bytes, StandardCharsets.UTF_8));
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    System.out.println("Failed");
//                    System.out.println(new String(bytes, StandardCharsets.UTF_8));
                }
            });
        }
        catch (Exception e)
        {

        }
    }
    /**
     * This is generally used when an user registers to the system,
     * to update the server and then to save him to the db if his information is valid
     * @param user
     */
    @Override
    public void RegisterUser(Object user)
    {
        System.out.println("Register user");
        boolean isTeacher = user instanceof Teacher;
        String URL = isTeacher ? BASE_URL + "/Teacher/Register" : BASE_URL + "/Student/Register";
        JsonObject jdata = json_builder(user);
        /**
         * example :
         * jdata.addProperty("Email", "mosheCohen@gmail.com");
         *  jdata.addProperty("Password", "123456");
         */

        client.addHeader("Content-Type", "application/json");
        try {
            StringEntity entity = new StringEntity(jdata.toString());
            client.post(this.context, URL, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    System.out.println("Success");
//                    System.out.println(new String(bytes, StandardCharsets.UTF_8));
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                    System.out.println(new String(bytes, StandardCharsets.UTF_8));
                    System.out.println("Failed");

                }
            });
        }
        catch (Exception e)
        {

        }
    }

    /**
     * This is used when a teacher appoints a lesson with a student,
     * to update the server, and to receive back information if the
     * lesson is valid.
     * @param lesson
     */
    @Override
    public void RegisterLesson(Lesson lesson) {

        JsonObject jdata = json_builder(lesson);
        try
        {
            StringEntity entity = new StringEntity(jdata.toString());
            client.post(this.context, BASE_URL + "/Lesson",entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    System.out.println(new String(bytes, StandardCharsets.UTF_8));
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    System.out.println(new String(bytes, StandardCharsets.UTF_8));
                }
            });
        }
        catch (Exception e)
        {

        }


    }

    /**
     * This is used to get all the objects available from the server
     * which reflect the object's class specified, such as Student,
     * Teacher, Lesson
     * This is used when the UI needs to update all the object available
     * on screen.
     *
     * @param T
     * @return
     */
    @Override
    public void GetAll(Object T) {
        String URL = BASE_URL;
        if(T instanceof Student)
            URL += "/Student";
        else if (T instanceof Teacher)
            URL += "/Teacher";
        else if (T instanceof Lesson)
            URL += "/Lesson";

        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                System.out.println("Success");
                System.out.println(new String(bytes, StandardCharsets.UTF_8));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.println("Failed");
                System.out.println(new String(bytes, StandardCharsets.UTF_8));
            }
        });
    }

    @Override
    public void GetAllLogged() {
        System.out.println("GetAllLogged");
        String URL = BASE_URL + "/Message";
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                System.out.println("Success");
                System.out.println(new String(bytes, StandardCharsets.UTF_8));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.println("Failed");
                System.out.println(new String(bytes, StandardCharsets.UTF_8));
            }
        });
    }

    private JsonObject json_builder(Object obj)
    {
        JsonObject jdata = new JsonObject();
        if(obj instanceof Student) {
            Student std = (Student) obj;
            jdata.addProperty("Email", std.email);
            jdata.addProperty("Name", std.name);
            jdata.addProperty("Phone", std.phone);
            jdata.addProperty("Password", std.password);
        }
        else if(obj instanceof Teacher) {
            Teacher teach = (Teacher) obj;
            jdata.addProperty("Email", teach.Email);
            jdata.addProperty("Name", teach.Name);
            jdata.addProperty("Phone", teach.Phone);
            jdata.addProperty("Password", teach.Password);
        }
        else if(obj instanceof Lesson)
        {
            Lesson lesson = (Lesson) obj;
            jdata.addProperty("TeacherId", lesson.teacher_id);
            jdata.addProperty("StudentId", lesson.Student_id);
            jdata.addProperty("Date", String.valueOf(lesson.date));
            jdata.addProperty("Comment", lesson.Comment);
        }
        return jdata;
    }
}
