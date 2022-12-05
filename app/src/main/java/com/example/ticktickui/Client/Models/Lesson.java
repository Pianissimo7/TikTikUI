package com.example.ticktickui.Client.Models;

import java.util.Date;

public class Lesson {
    public int id;
    public int teacher_id;
    public int Student_id;
    public Date date;
    public String Comment;
    public Lesson(int teacher_id, int student_id, Date date, String comment)
    {
        this.teacher_id = teacher_id;
        this.Student_id = student_id;
        this.date = date;
        this.Comment = comment;
    }
}
