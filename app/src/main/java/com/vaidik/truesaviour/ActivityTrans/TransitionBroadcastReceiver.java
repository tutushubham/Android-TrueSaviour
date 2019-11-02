package com.vaidik.truesaviour.ActivityTrans;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;

import java.util.ArrayList;

import io.paperdb.Paper;

public class TransitionBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ActivityTransitionResult.hasResult(intent)) {
            ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);
            if (result != null) {
                for (ActivityTransitionEvent event : result.getTransitionEvents()) {
                    saveActivity(new ActivityTransitionEventWrapper(event));
                }
            }
        }
    }

    public void saveActivity(final ActivityTransitionEventWrapper event) {
        ArrayList<ActivityTransitionEventWrapper> events = Paper.book().read("activities", new ArrayList<ActivityTransitionEventWrapper>());
        events.add(event);
        Paper.book().write("activities", events);
    }
}
