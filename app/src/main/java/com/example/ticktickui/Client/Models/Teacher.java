package com.example.ticktickui.Client.Models;

public class Teacher {
    public int id;
    public String Name;
    public String Email;
    public String Phone;
    public String Password;
    public Teacher(){}
    public Teacher(String Name, String Email, String Phone, String Password)
    {
        this.Name = Name;
        this.Email = Email;
        this.Phone = Phone;
        this.Password = Password;
    }
    // public List<Integer> students_by_id; 
}
