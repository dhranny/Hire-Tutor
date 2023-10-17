package com.example.androiid.models;

import com.google.gson.annotations.SerializedName;

public class RegisterModel {
    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    @SerializedName("type")
    public String role;

    @SerializedName("streetAddress")
    public String streetAddress;

    @SerializedName("firstname")
    public String firstname;

    @SerializedName("lastname")
    public String lastname;
}
