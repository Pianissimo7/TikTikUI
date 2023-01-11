package com.example.ticktickui.Client;

import android.content.Context;
import android.widget.Toast;

import com.example.ticktickui.Client.Models.Lesson;
import com.example.ticktickui.Client.Models.Response;
import com.example.ticktickui.Client.Models.Schedule;
import com.example.ticktickui.Client.Models.Teacher;
import com.example.ticktickui.EventEditActivity;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class ClientAndroid implements ClientInterface{
    private static final AsyncHttpClient client = new AsyncHttpClient();
    private static final String BASE_URL = "http://10.12.10.15:5000"; // Remember to update IP if changed...
//    private static String BASE_URL = "http://localhost:5231"; // Remember to update IP if changed...
    private static final String DEFAULT_ERR = "Something went wrong!";
    private static final String DEFAULT_EMAIL_PASS_ERR = "Email or password is not correct";
    private static final String EMAIL_EXISTS_ERR = "Email already exists";
    private final Context context;

    /**
     * To set Context, please use getBaseContext()
     * @param context a context
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
     * @param Email email of the user
     * @param Password password of the user
     *                 example:
     *          * j-data.addProperty("Email", "mosheCohen@gmail.com");
     *          *         j-data.addProperty("Password", "123456");
     */
    @Override
    public void LoginUser( String Email, String Password, Function<Object, Integer> callbackSuccess,
                           Function<String, Integer> callbackFailure)
    {
        JsonObject jdata = new JsonObject();
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
                    if(res.approved){
                        JsonObject jsonObject =  builder.create().fromJson(object, JsonObject.class);
                        if (res.isTeacher)
                        {
                            Teacher teacher = builder.create().fromJson(jsonObject.get("teacher"), Teacher.class);
                            callbackSuccess.apply(teacher);
                        }
                        else {
                            Student student = builder.create().fromJson(jsonObject.get("student"), Student.class);
                            GlobalVariables.teacher = builder.create().fromJson(jsonObject.get("teacher"), Teacher.class);
                            callbackSuccess.apply(student);
                        }
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    if(bytes != null && i == 404)
                        callbackFailure.apply(DEFAULT_EMAIL_PASS_ERR);
                    else
                        callbackFailure.apply(DEFAULT_ERR);
                }
            });
        }
        catch (Exception ignored)
        {

        }
    }

    /**
     * This is used to update the server that a user disconnected,
     * to make sure we can see the actual logged in users.
     * @param Email email of the user to disconnect
     * @param Password password of the user to disconnect
     *
     *          * example :
     *          * jdata.addProperty("Email", "mosheCohen@gmail.com");
     *          *         jdata.addProperty("Password", "123456");
     *
     */
    @Override
    public void DisconnectUser(String Email, String Password) {
        System.out.println("Disconnect user");

        JsonObject jdata = new JsonObject();

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
        catch (Exception ignored)
        {

        }
    }
    public void GetStudentById(int id,
                        Function<Integer, Integer> callbackSuccess,
                        Function<Integer, Integer> callbackFailure)
    {
        String URL = BASE_URL + "/Student/" + id;
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                callbackSuccess.apply(0);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                callbackFailure.apply(statusCode);
            }
        });

    }
    /**
     * This is generally used when an user registers to the system,
     * to update the server and then to save him to the db if his information is valid
     * @param user user to register might be Student or Teacher
     *             example :
     *          * jdata.addProperty("Email", "mosheCohen@gmail.com");
     *          *  jdata.addProperty("Password", "123456");
     */
    @Override
    public void RegisterUser(Object user, Function<Integer, Integer> callbackSuccess,
                             Function<String, Integer> callbackFailure)
    {
        System.out.println("Register user");
        boolean isTeacher = user instanceof Teacher;
        String URL = isTeacher ? BASE_URL + "/Teacher/Register" : BASE_URL + "/Student/Register";
        JsonObject jdata = json_builder(user);

        client.addHeader("Content-Type", "application/json");
        try {
            StringEntity entity = new StringEntity(jdata.toString());
            client.post(this.context, URL, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    GsonBuilder builder = new GsonBuilder();
                    int user_id = builder.create().fromJson(new String(bytes, StandardCharsets.UTF_8), int.class);
                    callbackSuccess.apply(user_id);
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    if(bytes != null && i == 400)
                        callbackFailure.apply(EMAIL_EXISTS_ERR);
                    else
                        callbackFailure.apply(DEFAULT_ERR);
                }
            });
        }
        catch (Exception ignored)
        {

        }
    }

    /**
     * This is used when a teacher appoints a lesson with a student,
     * to update the server, and to receive back information if the
     * lesson is valid.
     * @param lesson lesson to register
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
                    GsonBuilder builder = new GsonBuilder();
                    lesson.id = builder.create().fromJson(new String(bytes, StandardCharsets.UTF_8), int.class);
                    activity.setLesson(lesson);
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
     * @param T new Type of a Student or Lesson or Teacher
     * gets all the records of type T, and applies callbackSuccess on the result
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
    public void DeleteLesson(int LessonId, Function<Integer, Integer>callbackSuccess, Function<Integer, Integer> callbackFailure)
    {
        String URL = BASE_URL + "/Lesson" + "/" + LessonId;
        client.delete(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                callbackSuccess.apply(0);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Failed in Delete lesson, error message: " + statusCode + " " + LessonId);
                callbackFailure.apply(statusCode);
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
                    System.out.println(statusCode);
                    callbackFailure.apply(-1);
                }
            });
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteConnection(int StudentId, Function<Integer,Integer> callbackSuccess, Function<Integer,Integer> callbackFailure) {
        String URL = BASE_URL + "/Student/DeleteTeacher/" + StudentId;
        client.delete(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                callbackSuccess.apply(0);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                callbackFailure.apply(statusCode);
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
                callbackFail.apply(statusCode);
            }
        });
    }

    @Override
    public void GetLessonsByObj(int objId, boolean isTeacher, Function<ArrayList<Lesson>, Integer> callbackSuccess,
                                 Function<Integer, Integer> callbackFailure) {
        System.out.println("" + objId + " " + isTeacher);
        String URL = BASE_URL + "/Lesson/" + isTeacher + "/" + objId;
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
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
                }
                callbackSuccess.apply(lessons);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Failed " + statusCode);
                callbackFailure.apply(0);
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
    @Override
    public void GetTeacherWorkTimes(int TeacherId,
                             Function<Schedule, Integer> callbackSuccess,
                             Function<Integer, Integer> callbackFailure)
    {
        String URL = BASE_URL + "/Schedule/" + TeacherId;
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Schedule sched = Schedule.parser(new String(responseBody, StandardCharsets.UTF_8));
                callbackSuccess.apply(sched);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                callbackFailure.apply(0);
            }
        });

    }
    @Override
    public void UpdateTeacherWorkTimes(int TeacherId, Schedule schedule,
                                Function<Integer, Integer> callbackSuccess,
                                Function<Integer, Integer> callbackFailure)
    {
        System.out.println(schedule.toString());
        String URL = BASE_URL + "/Schedule/" + TeacherId;
        //             client.post(this.context, URL, entity, "application/json", new AsyncHttpResponseHandler() {
        try {
            StringEntity entity = new StringEntity(schedule.toString());
            client.post(this.context, URL, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    callbackSuccess.apply(0);
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    callbackFailure.apply(statusCode);
                }
            });
        }
        catch (Exception ignored)
        {

        }
    }
    public void UpdateDetails(int id , boolean isTeacher, Object new_obj,
                              Function<Integer, Integer> callbackSuccess,
                              Function<Integer, Integer> callbackFailure)
    {
        String URL = BASE_URL + (isTeacher ? "/Teacher/" : "/Student/") + id;
        JsonObject obj = json_builder(new_obj);
        obj.addProperty("id", id);
        try {
            StringEntity entity = new StringEntity(obj.toString());
            client.put(this.context, URL, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    callbackSuccess.apply(0);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    callbackFailure.apply(statusCode);
                }
            });
        }
        catch (Exception e)
        {

        }
    }

    public void GetNumberOfLessons(int studentId,
                            Function<Integer, Integer> callbackSuccess,
                            Function<Integer, Integer> callbackFailure)
    {
        String URL = BASE_URL + "/Lesson/AmountLessons/" + studentId;
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                GsonBuilder builder = new GsonBuilder();
                int num_lessons = builder.create().fromJson(new String(responseBody, StandardCharsets.UTF_8), int.class);
                callbackSuccess.apply(num_lessons);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                callbackFailure.apply(statusCode);
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
