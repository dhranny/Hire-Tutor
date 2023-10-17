package com.example.androiid.service;

import com.example.androiid.models.*;
import retrofit2.Call;

import retrofit2.http.*;

import java.util.Map;

public interface RetroInterface {
    @POST("/auth/login")
    Call<LoginResult> login(@Body LoginModel loginModel);

    @POST("/auth/register")
    Call<RegisterResult> register(@Body RegisterModel loginModel);

    @GET("/courses")
    Call<Courses> getCourse(@Header("Authorization") String token);

    @GET("/tutor-courses")
    Call<Courses> getMyCourse(@Header("Authorization") String token);

    @GET("/tutor-course/{tutor_id}")
    Call<Courses> getCourse(@Header("Authorization") String token, @Path("tutor_id") long id);
}
