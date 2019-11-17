package com.vaidik.truesaviour.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vaidik.truesaviour.R;
import com.vaidik.truesaviour.activites.Website;


public class Home extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        Button getStarted = view.findViewById(R.id.getStarted);
        Button talkNow = view.findViewById(R.id.talkNow);


        getStarted.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Website.class);
            startActivity(intent);
        });

        talkNow.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "COMING SOON!", Toast.LENGTH_LONG).show();
            /*Intent intent = new Intent(getContext(), NavFrag.class);
            intent.putExtra("page", "chat");
            startActivity(intent);*/
        });


        return view;
    }
}
