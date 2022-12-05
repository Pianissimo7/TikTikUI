package com.example.ticktickui.Client.Models;

public class Student {
    public int id;
    public String email;
    public String name;
    public String phone;
    public String password;
    // public Integer teacherid;
    public Student(String Name, String Email, String Phone, String Password)
    {
        this.name = Name;
        this.email = Email;
        this.phone = Phone;
        this.password = Password;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        
        return "" + id + " " + name + " " + email + " " + phone + " " + password;
    }
}
