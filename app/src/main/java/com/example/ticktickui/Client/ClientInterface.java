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


}
