package com.example.ticktickui.Client;

import android.content.Context;
import android.widget.Toast;

import com.example.ticktickui.Client.Models.Lesson;
import com.example.ticktickui.Client.Models.Response;
import com.example.ticktickui.Client.Models.Teacher;
import com.example.ticktickui.DailyCalendarActivity;
import com.example.ticktickui.EventEditActivity;
import com.example.ticktickui.MainActivity;
import com.example.ticktickui.global_variables.GlobalVariables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.loopj.android.http.*;

import com.example.ticktickui.Client.Models.Student;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class ClientAndroid implements ClientInterface{
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static String BASE_URL = "http://10.6.2.239:5231"; // Remmember to update IP if changed...
//    private static String BASE_URL = "http://localhost:5231"; // Remmember to update IP if changed...

    private Context context;
    private MainActivity mainActivity;

    public ClientAndroid(){}
    /**
     * To set Context, please use getBaseContext()
     * @param context
     */
    public ClientAndroid(Context context)
    {
        this.context = context;
        this.mainActivity = (MainActivity) context;
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
    public void LoginUser( String Email, String Password)
    {
        JsonObject jdata = new JsonObject();
        /**
         * example:
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
                    String object = new String(bytes, StandardCharsets.UTF_8);
                    GsonBuilder builder = new GsonBuilder();
                    Response res = builder.create().fromJson(object, Response.class);
                    if(!res.approved)
                        Toast.makeText(mainActivity.getBaseContext(), "wrong email or password", Toast.LENGTH_LONG).show();
                    else {
                        if (res.isTeacher)
                        {
                            JsonObject jsonObject =  builder.create().fromJson(object, JsonObject.class);
                            Teacher teacher = builder.create().fromJson(jsonObject.get("teacher"), Teacher.class);
                            GlobalVariables.UpdateFields(
                                    teacher.name,
                                    teacher.phone,
                                    teacher.email,
                                    teacher.id,
                                    true);
                            mainActivity.loginFragment.switch_to_home_teacher_activity();
                        }
                        else {
                            JsonObject jsonObject =  builder.create().fromJson(object, JsonObject.class);
                            Student student = builder.create().fromJson(jsonObject.get("student"), Student.class);
                            GlobalVariables.UpdateFields(
                                    student.name,
                                    student.phone,
                                    student.email,
                                    student.id,
                                    false);
                            GlobalVariables.teacher = builder.create().fromJson(jsonObject.get("teacher"), Teacher.class);
                            mainActivity.loginFragment.switch_to_home_student_activity();
                        }
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    if (i == 404) {
                        Toast.makeText(mainActivity.getBaseContext(), "wrong enail or password", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(mainActivity.getBaseContext(), "failed to connect to server", Toast.LENGTH_LONG).show();
                    }
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
                    // TODO implement me
                    System.out.println("Success");
//                    System.out.println(new String(bytes, StandardCharsets.UTF_8));
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    // TODO implement me
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
                    if(isTeacher) {
//                        Teacher teacher = (Teacher) user;
                        // mainActivity.loginFragment.login(teacher.email, teacher.password);
                        System.out.println("Registry was Successfull");
                    }
                    else{
//                        Student std = (Student) user;
                        // mainActivity.loginFragment.login(std.email, std.password);
                        System.out.println("Registry Successfull");
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    mainActivity.registerFragment.register_failed();
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
    public void RegisterLesson(EventEditActivity activity, Lesson lesson) {
        JsonObject jdata = json_builder(lesson);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted_Date = format.format(lesson.date);
        formatted_Date = formatted_Date.replace(" ", "T");
        jdata.addProperty("Date", formatted_Date);
        try
        {
            StringEntity entity = new StringEntity(jdata.toString());
            client.post(this.context, BASE_URL + "/Lesson", entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    activity.setLesson();
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Toast.makeText(activity.getBaseContext(), "Error setting lesson", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(activity.getParent().getApplicationContext(), "Error setting lesson", Toast.LENGTH_LONG).show();
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
    public void GetAll(Object T, Function<ArrayList<Object>, Integer> callbackSuccess, Function<Integer, Integer> callbackFail) {
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
                String strobject = new String(bytes, StandardCharsets.UTF_8);
                GsonBuilder builder = new GsonBuilder();
                if (T instanceof Student) {
                    Student[] students_arr = builder.create().fromJson(strobject, Student[].class);
                    ArrayList students = new ArrayList(Arrays.asList(students_arr));
                    callbackSuccess.apply(students);
                }
                else if (T instanceof Teacher) {
                    Teacher[] teachers_arr = builder.create().fromJson(strobject, Teacher[].class);
                    ArrayList teachers = new ArrayList(Arrays.asList(teachers_arr));
                    callbackSuccess.apply(teachers);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.println(i);
                callbackFail.apply(-1);
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
                // TODO implement me
                System.out.println("Success");
                System.out.println(new String(bytes, StandardCharsets.UTF_8));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                // TODO implement me
                System.out.println("Failed");
                System.out.println(new String(bytes, StandardCharsets.UTF_8));
            }
        });
    }
    @Override
    public void DeleteLesson(int LessonId)
    {
        String URL = BASE_URL + "/Lesson";
        client.delete(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // TODO implement me
                System.out.println("Success");
                System.out.println(new String(responseBody, StandardCharsets.UTF_8));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // TODO implement me
                System.out.println("Failed");
                System.out.println(new String(responseBody, StandardCharsets.UTF_8));
            }
        });
    }

    @Override
    public void ConnectStudentToTeacher(int StudentId, int TeacherId, Function<Integer, Integer> callbackSuccess, Function<Integer, Integer> callbackFailure) {
        String URL = BASE_URL + "/Student/AddTeacher/" + StudentId + "/" + TeacherId;
        JsonObject jdata = new JsonObject();
        try{
            StringEntity entity = new StringEntity(jdata.toString());
            client.post(this.context, URL , entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    callbackSuccess.apply(0);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    callbackFailure.apply(-1);
                }
            });
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteConnection(int StudentId) {
        String URL = BASE_URL + "/Student/DeleteTeacher/" + StudentId;
        client.delete(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // TODO implement me
                System.out.println("Success");
                System.out.println(new String(responseBody, StandardCharsets.UTF_8));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // TODO implement me
                System.out.println("Failed");
                System.out.println(new String(responseBody, StandardCharsets.UTF_8));
            }
        });
    }

    @Override
    public void GetStudentsByTeacher(int TeacherId, Function<ArrayList<Student>,Integer> callbackSuccess, Function<Integer,Integer> callbackFailure) {
        String URL = BASE_URL + "/Teacher/Students/" + TeacherId;
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String strobject = new String(responseBody, StandardCharsets.UTF_8);
                GsonBuilder builder = new GsonBuilder();
                Student[] student_arr =  builder.create().fromJson(strobject, Student[].class);
                ArrayList<Student> students = new ArrayList<>(Arrays.asList(student_arr));
                callbackSuccess.apply(students);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println(statusCode);
                callbackFailure.apply(-1);
            }
        });
    }
    @Override
    public void GetTeacherByStudent(int StudentId, Function<Teacher, Integer> callbackSuccess, Function<Integer, Integer> callbackFail)
    {
        String URL = BASE_URL + "/Student/Teacher/" + StudentId;
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                GsonBuilder builder = new GsonBuilder();
                String object = new String(responseBody, StandardCharsets.UTF_8);
                Teacher teacher = builder.create().fromJson(object, Teacher.class);
                callbackSuccess.apply(teacher);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                callbackFail.apply(0);
            }
        });
    }

    @Override
    public void GetLessonsByObj(DailyCalendarActivity activity, int objId, boolean isTeacher) {
        System.out.println("" + objId + " " + isTeacher);
        String URL = BASE_URL + "/Lesson/" + isTeacher + "/" + objId;
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                System.out.println("Success");
//                System.out.println(new String(responseBody, StandardCharsets.UTF_8));
                String strobject = new String(responseBody, StandardCharsets.UTF_8);
                GsonBuilder builder = new GsonBuilder();
                JsonObject[] json_objects =  builder.create().fromJson(strobject, JsonObject[].class);
                ArrayList<Lesson> lessons = new ArrayList<>();
                for(JsonObject obj : json_objects)
                {
                    int teachid = builder.create().fromJson(obj.get("teacherId"), int.class);
                    int stdid = builder.create().fromJson(obj.get("studentId"), int.class);
                    String date = builder.create().fromJson(obj.get("date"), String.class);
                    String comment = builder.create().fromJson(obj.get("comment"), String.class);
                    Lesson lesson = new Lesson(teachid, stdid, date, comment);
                    lesson.id = builder.create().fromJson(obj.get("id"), int.class);
                    lessons.add(lesson);
                    System.out.println(lesson);
                }
                activity.setLessonList(lessons);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // TODO implement me
                System.out.println("Failed");
                System.out.println(new String(responseBody, StandardCharsets.UTF_8));
            }
        });
    }

    @Override
    public void GetTeachersByName(String name) {
        String URL = BASE_URL + "/Teacher/TeacherByName/" + name;
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // TODO implement me
                System.out.println("Success");
                System.out.println(new String(responseBody, StandardCharsets.UTF_8));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // TODO implement me
                System.out.println("Failed");
                System.out.println(new String(responseBody, StandardCharsets.UTF_8));
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
            jdata.addProperty("Email", teach.email);
            jdata.addProperty("Name", teach.name);
            jdata.addProperty("Phone", teach.phone);
            jdata.addProperty("Password", teach.password);
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
