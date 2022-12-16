package com.example.ticktickui;

import com.example.ticktickui.Client.Models.Lesson;

import java.time.LocalTime;

public class HourEvent
{
    LocalTime time;
    Lesson lesson;

    public HourEvent(LocalTime time, Lesson lesson)
    {
        this.time = time;
        this.lesson = lesson;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }

}
