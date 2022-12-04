package com.example.ticktickui.Client.Models;

import java.util.List;

/**
 * This class is the response you get from the server after you've sent Login message
 * More specifically, u get one of two classes that inherit Response i.e Response_Student and Response_Teacher
 *
 */
public class Response
{
    // if the login details are fine, the response will be approved,
    // else this value will be false
    public boolean approved;
    // if the logged used is a teacher or a student
    public boolean isTeacher;
    public Response(){}
}


