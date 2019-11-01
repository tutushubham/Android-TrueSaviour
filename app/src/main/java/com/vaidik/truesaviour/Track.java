package com.vaidik.truesaviour;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Track extends Fragment {


    // TextView tvLog;
    private int[] detectedActivity = new int[]{
            DetectedActivity.IN_VEHICLE,
            DetectedActivity.ON_BICYCLE,
            // DetectedActivity.ON_FOOT,
            DetectedActivity.RUNNING,
            DetectedActivity.STILL,
            // DetectedActivity.TILTING,
            // DetectedActivity.UNKNOWN,
            DetectedActivity.WALKING};
    private RecyclerView recyclerView;
    ScrollView sc;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track, container, false);


        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new VegaLayoutManager());

        //tvLog = view.findViewById(R.id.tvLog);

        Intent intent = new Intent(getContext(), TransitionBroadcastReceiver.class);
        PendingIntent pendingIntentBroadcast = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        List<ActivityTransition> transitions = getTransitionActivityList();
        ActivityTransitionRequest request = new ActivityTransitionRequest(transitions);

        startGetBroadcast(pendingIntentBroadcast, request, "pendingIntentBroadcast");

        refresh();
        ArrayList<ActivityTransitionEventWrapper> events = Paper.book().read("activities", new ArrayList<ActivityTransitionEventWrapper>());


        FloatingActionButton fab = view.findViewById(R.id.action_refresh);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
                recyclerView.scrollToPosition(0);
                Toast.makeText(getContext(), " Refreshing...", Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }

    private void refresh() {
        ArrayList<ActivityTransitionEventWrapper> events = Paper.book().read("activities", new ArrayList<ActivityTransitionEventWrapper>());
        List<String> inputActi = new ArrayList<>();
        List<String> inputTimei = new ArrayList<>();
        List<String> inputTransi = new ArrayList<>();

        if (events.size() > 0) {
            //tvLog.setText("");
            for (int i = 0; i < (events.size() > 100 ? 100 : events.size()); i++) {
                inputActi.add(events.get(i).getEventName());
                inputTransi.add(events.get(i).getTransitionName());
                inputTimei.add(events.get(i).getEventTime());

                //tvLog.setText(events.get(i).getEventDisplayFormat() + "\n" + tvLog.getText().toString());
            }
        }

        RecyclerView.Adapter mAdapter = new Adapter(inputActi, inputTimei, inputTransi);
        recyclerView.setAdapter(mAdapter);
    }


    private void startGetBroadcast(PendingIntent pendingIntent, ActivityTransitionRequest request, final String type) {
        // myPendingIntent is the instance of PendingIntent where the app receives callbacks.
        Task<Void> task = ActivityRecognition.getClient(getContext()).requestActivityTransitionUpdates(request, pendingIntent);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(getContext(), "Waiting for Activity Transitions...", Toast.LENGTH_LONG).show();
            }
        });
        task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //Toast.makeText(context, "oncomplete " + type, Toast.LENGTH_SHORT).show();
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getContext(), "Error : " + e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<ActivityTransition> getTransitionActivityList() {
        List<ActivityTransition> transitions = new ArrayList<>();
        for (int activity : detectedActivity) {
            transitions.add(
                    new ActivityTransition.Builder()
                            .setActivityType(activity)
                            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                            .build());
            transitions.add(
                    new ActivityTransition.Builder()
                            .setActivityType(activity)
                            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                            .build());
        }
        return transitions;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
