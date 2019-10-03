package com.vaidik.truesaviour.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vaidik.truesaviour.R;
import com.vaidik.truesaviour.activites.LoginActivity;
import com.yayandroid.rotatable.Rotatable;


public class Home extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        Button getStarted = view.findViewById(R.id.getStarted);
        Button talkNow = view.findViewById(R.id.talkNow);

        new Rotatable.Builder(view.findViewById(R.id.heading))
                .direction(Rotatable.ROTATE_X)
                .build();

        new Rotatable.Builder(view.findViewById(R.id.img1))
                .direction(Rotatable.ROTATE_Y)
                .build();

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        talkNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NavFrag.class);
                intent.putExtra("page", "chat");
                startActivity(intent);
            }
        });


        return view;
    }
}
