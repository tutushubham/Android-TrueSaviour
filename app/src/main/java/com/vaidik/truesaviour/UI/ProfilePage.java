package com.vaidik.truesaviour.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vaidik.truesaviour.R;
import com.vaidik.truesaviour.activites.Website;

public class ProfilePage extends Fragment {

    private GoogleSignInClient mGoogleSignInClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView imageView = view.findViewById(R.id.image_view_profile);
        TextView textViewName = view.findViewById(R.id.user_profile_name);
        TextView textViewEmail = view.findViewById(R.id.user_profile_email);
        TextView fullName = view.findViewById(R.id.full_name);
        TextView fullEmail = view.findViewById(R.id.full_email);
        //TextView fullPhone = view.findViewById(R.id.full_phone);
        Button upgrade = view.findViewById(R.id.accountUpgrade);

        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .requestProfile()
                        .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            Glide.with(getActivity())
                    .load(user.getPhotoUrl().toString())
                    .into(imageView);


            textViewName.setText(user.getDisplayName());
            textViewEmail.setText(user.getEmail());
            fullName.setText(user.getDisplayName());
            fullEmail.setText(user.getEmail());
            //fullPhone.setText(user.getPhoneNumber());

            upgrade.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), Website.class);
                startActivity(intent);
            });


        }
        return view;
    }
}
