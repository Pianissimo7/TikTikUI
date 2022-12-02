package com.example.ticktickui.Client;

import com.example.ticktickui.Client.Models.Lesson;
import java.lang.reflect.Type;

public interface ClientInterface {

    public void LoginUser(String Email, String Password);
    public void DisconnectUser(String Email, String Password);
    public void RegisterUser(Object user);
    public void RegisterLesson(Lesson lesson);
    public void GetAll(Object T);
    public void GetAllLogged(); // TODO add a seperate logged in for students and for teachers if idan needs
    // TODO add Remove lesson {Both to teacher and student}
    // TODO Get Number of Lessons completed so far
    // TODO add student to teacher {first case: approved, then send ok, second case: not approved, send not ok(send comment)}
    // TODO remove student from teacher {first case: approved, then send ok, second case: not approved, send not ok(send comment)}
    // TODO Get all students by teacher
    // TODO Get teacher by student
    // TODO Get Lessons by teacher
    // TODO Get Lessons by student
    // TODO Get Teachers by name


}
