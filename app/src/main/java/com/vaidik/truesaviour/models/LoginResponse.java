package com.vaidik.truesaviour.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    public String username;
    public String password;
    @SerializedName("message")
    private String message;

    public LoginResponse(String username, String password) {
        Log.e("Response: ", "login pre");
        this.username = username;
        this.password = password;

    }


    public String getMessage() {
        Log.e("Response: ", "retmsg  pre");
        return message;
    }

}
