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
import com.vaidik.truesaviour.activites.SelfTest;


public class Dashboard extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selftest, container, false);


        Button selfTest = view.findViewById(R.id.startTest);

        selfTest.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SelfTest.class);
            startActivity(intent);
        });

        return view;
    }
}
