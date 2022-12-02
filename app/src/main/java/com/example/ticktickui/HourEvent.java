package com.example.ticktickui;

import java.time.LocalTime;
import java.util.ArrayList;

public class HourEvent
{
    LocalTime time;
    Event event;

    public HourEvent(LocalTime time, Event event)
    {
        this.time = time;
        this.event = event;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }

    public Event getEvents()
    {
        return event;
    }

    public void setEvents(Event event)
    {
        this.event = event;
    }
}
