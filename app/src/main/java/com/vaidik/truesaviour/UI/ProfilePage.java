package com.vaidik.truesaviour.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vaidik.truesaviour.R;
import com.vaidik.truesaviour.Utils.Session;

public class ProfilePage extends Fragment {

    TextView tv_user_profile_id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        tv_user_profile_id = root.findViewById(R.id.user_profile_name);
        tv_user_profile_id.setText(new Session(getActivity()).getUsername());
        return root;


    }
}
