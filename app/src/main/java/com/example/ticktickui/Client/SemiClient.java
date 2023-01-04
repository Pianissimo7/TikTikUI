package com.example.ticktickui.Client;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.function.Function;

public class SemiClient {

    private LocalTime[] startTimes;
    private LocalTime[] endTimes;
    private static LocalTime DEFAULT_START_TIME = LocalTime.of(7,0);
    private static LocalTime DEFAULT_END_TIME = LocalTime.of(22,0);
    public SemiClient()
    {
        startTimes = new LocalTime[7];
        for(int i=0; i< 7; i++)
        {
            startTimes[i] = DEFAULT_START_TIME;
        }
        endTimes = new LocalTime[7];
        for(int i=0; i< 7; i++)
        {
            endTimes[i] = DEFAULT_END_TIME;
        }
    }


    public void GetTeacherWorkTimes(int id, boolean success_failure,
                             Function<ArrayList<LocalTime[]>, Integer> callbackSuccess,
                             Function<Integer, Integer> callbackFailure)
    {
        // add worktimes if you want



        // end
        ArrayList<LocalTime[]> times = new ArrayList<>();
        times.add(startTimes);
        times.add(endTimes);
        if(success_failure)
            callbackSuccess.apply(times);
        else
            callbackFailure.apply(0);
    }
    public void UpdateTeacherWorkTimes(int id, boolean success_failure, LocalTime[] startTimes, LocalTime[] endTimes,
                                Function<Integer, Integer> callbackSuccess,
                                Function<Integer, Integer> callbackFailure)
    {
        if(startTimes != null)
            this.startTimes = startTimes;
        if(endTimes != null)
            this.endTimes = endTimes;
        if(success_failure)
            callbackSuccess.apply(0);
        else
            callbackFailure.apply(0);
    }



}
