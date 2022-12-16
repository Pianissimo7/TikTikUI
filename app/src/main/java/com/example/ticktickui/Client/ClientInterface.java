package com.example.ticktickui.Client;

import com.example.ticktickui.Client.Models.Lesson;
import com.example.ticktickui.Client.Models.Student;
import com.example.ticktickui.Client.Models.Teacher;
import com.example.ticktickui.DailyCalendarActivity;
import com.example.ticktickui.EventEditActivity;
import com.example.ticktickui.LoginFragment;
import com.example.ticktickui.RegisterFragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.function.Function;

public interface ClientInterface {

    public void LoginUser(String Email, String Password,
                          Function<Object, Integer> callbackSuccess,
                          Function<Integer, Integer> callbackFailure);
    public void DisconnectUser(String Email, String Password);
    public void RegisterUser( Object user);
    public void RegisterLesson(EventEditActivity activity, Lesson lesson);
    public void GetAll(Object T, Function<ArrayList<Object>, Integer> callbackSuccess, Function<Integer, Integer> callbackFail);
    public void GetAllLogged(); // TODO add a seperate logged in for students and for teachers if idan needs
    public void DeleteLesson(int LessonId, Function<Integer, Integer>callbackSucces, Function<Integer, Integer> callbackFailure);
    // TODO Get Number of Lessons completed so far **********
    public void ConnectStudentToTeacher(int StudentId, int TeacherId, Function<Integer, Integer> callbackSuccess, Function<Integer, Integer> callbackFailure);
    public void DeleteConnection(int StudentId);
    public void GetStudentsByTeacher(int TeacherId, Function<ArrayList<Student>,Integer> callbackSuccess, Function<Integer,Integer> callbackFailure);
    public void GetTeacherByStudent(int StudentId, Function<Teacher, Integer> callbackSuccess, Function<Integer, Integer> callbackFail);
    public void GetLessonsByObj(int objId, boolean isTeacher,
                                Function<ArrayList<Lesson>, Integer> callbackSuccess,
                                Function<Integer, Integer> callbackFailure);
    public void GetTeachersByName(String name);

}
