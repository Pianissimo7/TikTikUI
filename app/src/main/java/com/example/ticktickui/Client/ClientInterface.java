package com.example.ticktickui.Client;

import com.example.ticktickui.Client.Models.Lesson;
import com.example.ticktickui.Client.Models.Schedule;
import com.example.ticktickui.Client.Models.Student;
import com.example.ticktickui.Client.Models.Teacher;
import com.example.ticktickui.EventEditActivity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.function.Function;

public interface ClientInterface {

    void LoginUser(String Email, String Password,
                   Function<Object, Integer> callbackSuccess,
                   Function<String, Integer> callbackFailure);
    void DisconnectUser(String Email, String Password);
    void RegisterUser(Object user, Function<Integer, Integer> callbackSuccess,
                      Function<String, Integer> callbackFailure);
    void RegisterLesson(EventEditActivity activity, Lesson lesson);
    void GetAll(Object T, Function<ArrayList<Object>, Integer> callbackSuccess, Function<Integer, Integer> callbackFail);
    void GetAllLogged(); // TODO add a separate logged in for students and for teachers if Idan needs
    void DeleteLesson(int LessonId, Function<Integer, Integer> callbackSuccess, Function<Integer, Integer> callbackFailure);
    // TODO Get Number of Lessons completed so far **********
    void ConnectStudentToTeacher(int StudentId, int TeacherId, Function<Integer, Integer> callbackSuccess, Function<Integer, Integer> callbackFailure);
    void DeleteConnection(int StudentId, Function<Integer,Integer> callbackSuccess, Function<Integer,Integer> callbackFailure);
    void GetStudentsByTeacher(int TeacherId, Function<ArrayList<Student>, Integer> callbackSuccess, Function<Integer, Integer> callbackFailure);
    void GetTeacherByStudent(int StudentId, Function<Teacher, Integer> callbackSuccess, Function<Integer, Integer> callbackFail);
    void GetLessonsByObj(int objId, boolean isTeacher,
                         Function<ArrayList<Lesson>, Integer> callbackSuccess,
                         Function<Integer, Integer> callbackFailure);
    void GetNumberOfLessons(int studentId,
                            Function<Integer, Integer> callbackSuccess,
                            Function<Integer, Integer> callbackFailure);
    void GetTeachersByName(String name);
    void GetTeacherWorkTimes(int TeacherId,
                             Function<Schedule, Integer> callbackSuccess,
                             Function<Integer, Integer> callbackFailure);
    void UpdateTeacherWorkTimes(int TeacherId, Schedule schedule,
                                Function<Integer, Integer> callbackSuccess,
                                Function<Integer, Integer> callbackFailure);
    void UpdateDetails(int id , boolean isTeacher, Object new_obj,
                       Function<Integer, Integer> callbackSuccess,
                       Function<Integer, Integer> callbackFailure);



}
