package com.example.androiid.models;

import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("type")
    public String type;
}
