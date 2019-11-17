package com.vaidik.truesaviour.models;

public class ActivityTrack {

    private String actName;
    private String actTime;
    private String actState;
    private String actID;
    String email;

    public ActivityTrack() {

    }

    public ActivityTrack(String actName, String actTime, String actState, String actID, String email) {
        this.actName = actName;
        this.actTime = actTime;
        this.actState = actState;
        this.actID = actID;
        this.email = email;
    }

    public String getActName() {
        return actName;
    }

    public String getActID() {
        return actID;
    }

    public void setActID(String actID) {
        this.actID = actID;
    }

    public String getActTime() {
        return actTime;
    }

    public String getActState() {
        return actState;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public void setActTime(String actTime) {
        this.actTime = actTime;
    }

    public void setActState(String actState) {
        this.actState = actState;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
