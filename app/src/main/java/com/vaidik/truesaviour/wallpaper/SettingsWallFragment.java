package com.vaidik.truesaviour.wallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.vaidik.truesaviour.R;

public class SettingsWallFragment extends Fragment {

    private static final int GOOGLE_SIGN_IN_CODE = 212;
    private GoogleSignInClient mGoogleSignInClient;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return inflater.inflate(R.layout.fragment_wallpaper_settings_default, container, false);
        }
        return inflater.inflate(R.layout.fragment_wallpaper_settings_loggedin, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .requestProfile()
                        .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            ImageView imageView = view.findViewById(R.id.image_view);
            TextView textViewName = view.findViewById(R.id.text_view_name);
            TextView textViewEmail = view.findViewById(R.id.text_view_email);

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            Glide.with(getActivity())
                    .load(user.getPhotoUrl().toString())
                    .into(imageView);


            textViewName.setText(user.getDisplayName());
            textViewEmail.setText(user.getEmail());


            view.findViewById(R.id.text_view_logout).setOnClickListener(view1 -> {
                FirebaseAuth.getInstance().signOut();
                mGoogleSignInClient.signOut().addOnCompleteListener(task -> getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_area, new SettingsWallFragment())
                        .commit());


            });

        } else {

            view.findViewById(R.id.button_google_sign_in).setOnClickListener(view12 -> {
                Intent intent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent, GOOGLE_SIGN_IN_CODE);
            });
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(),
                task -> {
                    if (task.isSuccessful()) {

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_area, new SettingsWallFragment())
                                .commit();

                    } else {
                        Toast.makeText(getActivity(), "Login Failure", Toast.LENGTH_LONG).show();
                    }
                });
    }

}
