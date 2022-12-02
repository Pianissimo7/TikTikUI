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
    public void DeleteLesson(int LessonId);
    // TODO Get Number of Lessons completed so far **********
    public void ConnectStudentToTeacher(int StudentId, int TeacherId);
    public void DeleteConnection(int StudentId);
    public void GetStudentsByTeacher(int TeacherId);
    public void GetTeacherByStudent(int StudentId);
    public void GetLessonsByObj(int objId, boolean isTeacher);
    public void GetTeachersByName(String name);

}
