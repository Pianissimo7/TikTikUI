package com.example.ticktickui.Client.Models;

public class Teacher {
    public int id;
    public String name;
    public String email;
    public String phone;
    public String password;
    public Teacher(){}
    public Teacher(String name, String email, String phone, String password)
    {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
    // public List<Integer> students_by_id; 
}
