package com.vaidik.truesaviour.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void clearSession() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    public String getUsername() {
        String user_name = prefs.getString(StringUtils.USERNAME, StringUtils.INVALID_SESSION);
        return user_name;
    }

    public void setUsername(String user_name) {
        prefs.edit().putString(StringUtils.USERNAME, user_name).commit();
    }
}