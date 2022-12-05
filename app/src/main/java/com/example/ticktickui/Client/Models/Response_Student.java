package com.example.ticktickui.Client.Models;

import java.util.ArrayList;
import java.util.List;

public class Response_Student extends Response {
    public Teacher teacher; // the teacher this student is registered to
    public Student student;
    public ArrayList<Lesson> lessons; // the lessons this student has, you might not need it directly

    public Response_Student() {}

}
