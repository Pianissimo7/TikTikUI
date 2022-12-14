package com.example.ticktickui.Client.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Schedule {

    public LocalTime[] start_times;
    public LocalTime[] end_times;

    public Schedule() {
        start_times = new LocalTime[7];
        end_times = new LocalTime[7];
    }
    public static Schedule parser(String bytes) {
        GsonBuilder builder = new GsonBuilder();
        JsonObject json_obj = builder.create().fromJson(bytes, JsonObject.class);
        Schedule sched = new Schedule();
        String[] start_times_list = builder.create().fromJson(json_obj.get("StartTime"), String[].class);
        String[] end_times_list = builder.create().fromJson(json_obj.get("EndTime"), String[].class);
        ArrayList<LocalTime> arr = new ArrayList<>();
        ArrayList<LocalTime> finalArr = arr;
        Arrays.stream(start_times_list).forEach((time) -> {
            finalArr.add(LocalTime.parse(time, DateTimeFormatter.ofPattern("H:mm")));});
        sched.start_times =  arr.toArray(new LocalTime[7]);
        arr = new ArrayList<>();
        ArrayList<LocalTime> finalArr1 = arr;
        Arrays.stream(end_times_list).forEach((time) -> finalArr1.add(LocalTime.parse(time)));
        sched.end_times = arr.toArray(new LocalTime[7]);
        return sched;
    }
    public String toString()
    {
        JsonObject jdata = new JsonObject();
        int num_weeks= 7;
        JsonArray start_times_list =new JsonArray(num_weeks);
        JsonArray end_times_list = new JsonArray(num_weeks);
        for(int i =0; i< num_weeks; i++)
        {
            start_times_list.add(start_times[i].toString());
            end_times_list.add(end_times[i].toString());
        }
        jdata.add("Starts", start_times_list);
        jdata.add("Ends", end_times_list);
        return jdata.toString();
    }
}
