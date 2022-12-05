package com.example.ticktickui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static Event eventForDateAndTime(LocalDate date, LocalTime time)
    {
        for (Event event : eventsList)
        {
            LocalTime eventHour = event.time;
            LocalTime cellHour = time;
            if (event.getDate().equals(date) && eventHour.equals(cellHour))
                return event;
        }
        return null;
    }

    private String pick_up_place = "";
    private LocalDate date;
    private LocalTime time;

    public Event(String pick_up_place, LocalDate date, LocalTime time)
    {
        this.pick_up_place = pick_up_place;
        this.date = date;
        this.time = time;
    }

    public String get_pick_up_place()
    {
        return pick_up_place;
    }

    public void set_pick_up_place(String pick_up_place)
    {
        this.pick_up_place = pick_up_place;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
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
