package com.example.ticktickui.Client.Models;

import java.util.List;

public class Response_Teacher extends Response {
    public List<Student> students; // The list of students t his teacher has
    public List<Lesson> lessons; // the list of lessons this teacher registered
    public Response_Teacher(){};

}
