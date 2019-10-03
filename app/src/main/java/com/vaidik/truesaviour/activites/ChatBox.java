package com.vaidik.truesaviour.activites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vaidik.truesaviour.R;


public class ChatBox extends Fragment {

    private String channelID = "CHANNEL_ID_FROM_YOUR_SCALEDRONE_DASHBOARD";
    private String roomName = "observable-room";
    private EditText editText;
    //private Scaledrone scaledrone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);


        return view;

    }
}
