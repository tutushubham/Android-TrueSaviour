package com.vaidik.truesaviour;

import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.DetectedActivity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.google.android.gms.location.ActivityTransition.ACTIVITY_TRANSITION_ENTER;
import static com.google.android.gms.location.ActivityTransition.ACTIVITY_TRANSITION_EXIT;

public class ActivityTransitionEventWrapper {

    private static HashMap<Integer, String> activityTypeMap = new HashMap<>();
    private static HashMap<Integer, String> transitionTypeMap = new HashMap<>();
    private ActivityTransitionEvent event;
    private long timestamp = 0;

    ActivityTransitionEventWrapper(ActivityTransitionEvent event) {
        this.event = event;
        this.timestamp = System.currentTimeMillis();
    }

    public static String getActivityTypeDesc(int type) {
        if (activityTypeMap.size() == 0) {
            activityTypeMap.put(DetectedActivity.IN_VEHICLE, "IN_VEHICLE");
            activityTypeMap.put(DetectedActivity.ON_BICYCLE, "ON_BICYCLE");
            activityTypeMap.put(DetectedActivity.RUNNING, "RUNNING");
            activityTypeMap.put(DetectedActivity.STILL, "STILL");
            activityTypeMap.put(DetectedActivity.WALKING, "WALKING");
        }
        return activityTypeMap.get(type);
    }

    public static String getTransitionTypeDesc(int type) {
        if (transitionTypeMap.size() == 0) {
            transitionTypeMap.put(ACTIVITY_TRANSITION_ENTER, "ACTIVITY_TRANSITION_ENTER");
            transitionTypeMap.put(ACTIVITY_TRANSITION_EXIT, "ACTIVITY_TRANSITION_EXIT");
        }
        return transitionTypeMap.get(type);
    }

    String getEventDisplayFormat() {
        String datetime = convertTime(timestamp);
        String text = datetime + "\n";
        text += getActivityTypeDesc(event.getActivityType()) + "\n";
        text += getTransitionTypeDesc(event.getTransitionType()) + "\n";
        // text += event.getElapsedRealTimeNanos() +"\n";
        return text;
    }

    String getEventName() {
        // String datetime = convertTime(timestamp);
        // String text = datetime + "\n";
        //text += getTransitionTypeDesc(event.getTransitionType()) + "\n";
        // text += event.getElapsedRealTimeNanos() +"\n";
        return getActivityTypeDesc(event.getActivityType());
    }

    String getTransitionName() {
        // String datetime = convertTime(timestamp);
        //String text = datetime + "\n";
        //text = getActivityTypeDesc(event.getActivityType()) + "\n";
        // text += event.getElapsedRealTimeNanos() +"\n";
        return getTransitionTypeDesc(event.getTransitionType());
    }

    String getEventTime() {
        //text = getActivityTypeDesc(event.getActivityType()) + "\n";
        //String text = getTransitionTypeDesc(event.getTransitionType()) + "\n";
        // text += event.getElapsedRealTimeNanos() +"\n";
        return convertTime(timestamp);
    }

    public String convertTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
