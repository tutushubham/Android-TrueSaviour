package com.vaidik.truesaviour.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    public String username;
    public String password;
    @SerializedName("message")
    private String message;

    public LoginResponse(String username, String password) {
        this.username = username;
        this.password = password;

    }


    public String getMessage() {
        return message;
    }

}
