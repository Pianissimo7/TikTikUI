package com.example.ticktickui.Client.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Lesson {
    public int id;
    public int teacher_id;
    public int Student_id;
    public LocalDateTime date;
    public String Comment;
    public Lesson(int teacher_id, int student_id, LocalDateTime date, String comment)
    {
        this.teacher_id = teacher_id;
        this.Student_id = student_id;
        this.date = date;
        this.Comment = comment;
    }
    public Lesson(int teacher_id, int student_id, String date, String comment)
    {
        this.teacher_id = teacher_id;
        this.Student_id = student_id;
        this.date = LocalDateTime.parse(date);
        this.Comment = comment;
    }
    @Override
    public String toString()
    {
        return this.teacher_id + " " + this.Student_id + " " + this.date + " " + this.Comment;
    }

}
