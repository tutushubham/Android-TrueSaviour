package com.vaidik.truesaviour.activites;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stone.vega.library.VegaLayoutManager;
import com.vaidik.truesaviour.ActivityTrans.ActivityTransitionEventWrapper;
import com.vaidik.truesaviour.ActivityTrans.TransitionBroadcastReceiver;
import com.vaidik.truesaviour.Adapters.Adapter;
import com.vaidik.truesaviour.R;
import com.vaidik.truesaviour.models.ActivityTrack;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class TrackAct extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Activites");
    private int[] detectedActivity = new int[]{
            DetectedActivity.IN_VEHICLE,
            DetectedActivity.ON_BICYCLE,
            DetectedActivity.RUNNING,
            DetectedActivity.STILL,
            DetectedActivity.WALKING};
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new VegaLayoutManager());

        //tvLog = view.findViewById(R.id.tvLog);

        Intent intent = new Intent(this, TransitionBroadcastReceiver.class);
        PendingIntent pendingIntentBroadcast = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        List<ActivityTransition> transitions = getTransitionActivityList();
        ActivityTransitionRequest request = new ActivityTransitionRequest(transitions);

        startGetBroadcast(pendingIntentBroadcast, request);


        ArrayList<ActivityTransitionEventWrapper> events = Paper.book().read("activities", new ArrayList<>());
        refresh(events);

        FloatingActionButton fab = findViewById(R.id.action_refresh);
        fab.setOnClickListener(view1 -> {
            refresh(events);
            Toast.makeText(this, " Syncing...COME BACK LATER!!!", Toast.LENGTH_LONG).show();
        });

    }

    private void refresh(ArrayList<ActivityTransitionEventWrapper> events) {
        //ArrayList<ActivityTransitionEventWrapper> events = Paper.book().read("activities", new ArrayList<>());
        List<String> inputActi = new ArrayList<>();
        List<String> inputTimei = new ArrayList<>();
        List<String> inputTransi = new ArrayList<>();

        String actID;
        databaseReference.keepSynced(true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        ActivityTrack act = new ActivityTrack("", "", "", "", "");
        act.setEmail(user.getEmail());

        if (events.size() > 0) {
            //tvLog.setText("");
            for (int i = 0; i < (events.size() > 100 ? 100 : events.size()); i++) {
                act.setActName(events.get(i).getEventName());
                act.setActTime(events.get(i).getEventTime());
                act.setActState(events.get(i).getTransitionName());
                actID = databaseReference.push().getKey();
                act.setActID(actID);

                databaseReference.child(actID).setValue(act);

            }
        }

        Paper.book().delete("activities");


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    ActivityTrack retAct = snap.getValue(ActivityTrack.class);
                    inputActi.add(retAct.getActName());
                    inputTimei.add(retAct.getActTime());
                    inputTransi.add(retAct.getActState());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        RecyclerView.Adapter mAdapter = new Adapter(inputActi, inputTimei, inputTransi);
        recyclerView.setAdapter(mAdapter);


    }


    private void startGetBroadcast(PendingIntent pendingIntent, ActivityTransitionRequest request) {
        // myPendingIntent is the instance of PendingIntent where the app receives callbacks.
        Task<Void> task = ActivityRecognition.getClient(this).requestActivityTransitionUpdates(request, pendingIntent);
        task.addOnSuccessListener(result -> Toast.makeText(this, "Waiting for Activity Transitions...", Toast.LENGTH_LONG).show());
        task.addOnCompleteListener(task1 -> {
            //Toast.makeText(context, "on complete " + type, Toast.LENGTH_SHORT).show();
        });
        task.addOnFailureListener(e -> Toast.makeText(this, "Error : " + e.toString(), Toast.LENGTH_LONG).show());
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
