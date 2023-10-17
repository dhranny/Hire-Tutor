package com.example.androiid.models;

import com.google.gson.annotations.SerializedName;

public class LoginResult {
    @SerializedName("message")
    public String message;
    @SerializedName("success")
    public boolean success;
    @SerializedName("data")
    public String data;
}
