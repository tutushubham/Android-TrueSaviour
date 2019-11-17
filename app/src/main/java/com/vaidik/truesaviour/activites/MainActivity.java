package com.vaidik.truesaviour.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.vaidik.truesaviour.ActivityTrans.TrackLoad;
import com.vaidik.truesaviour.R;
import com.vaidik.truesaviour.UI.Dashboard;
import com.vaidik.truesaviour.UI.Home;
import com.vaidik.truesaviour.UI.NavFrag;

import java.util.Objects;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView tv_user_id_display;
    Intent intent;

    FloatingNavigationView mFloatingNavigationView;
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
                return true;
            case R.id.navigation_dashboard:
                Log.e("second frag", "self test");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Dashboard()).commit();
                return true;
            case R.id.navigation_notifications:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TrackLoad()).commit();
                return true;
        }
        return false;
    };

    //private Session session;
    BottomNavigationView navigation;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .requestProfile()
                        .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //session = new Session(MainActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        mFloatingNavigationView = findViewById(R.id.nav_view);
        View headerView = mFloatingNavigationView.getHeaderView(0);
        tv_user_id_display = headerView.findViewById(R.id.user_id_display);
        tv_user_id_display.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        ImageView imageView = headerView.findViewById(R.id.image_view_profile_small);
        Glide.with(this)
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())
                .into(imageView);

        headerView.findViewById(R.id.image_view_profile_small).setOnClickListener(view1 -> {
            intent = new Intent(this, NavFrag.class);
            intent.putExtra("page", "profile");
            startActivity(intent);
        });

        mFloatingNavigationView.setNavigationItemSelectedListener(this);
        mFloatingNavigationView.setOnClickListener(view -> mFloatingNavigationView.open());


        navigation = findViewById(R.id.navigationMain);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();


        Paper.init(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_chat:
                Toast.makeText(this, "COMING SOON!", Toast.LENGTH_LONG).show();
                /*intent = new Intent(this, TSbot.class);
                startActivity(intent);*/
                break;
            case R.id.nav_profile:
                intent = new Intent(this, NavFrag.class);
                intent.putExtra("page", "profile");
                startActivity(intent);
                break;
            case R.id.nav_contact:
                intent = new Intent(this, NavFrag.class);
                intent.putExtra("page", "contact");
                startActivity(intent);
                break;
            case R.id.wall:
                intent = new Intent(this, WallpaperMainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_login: //later to be replaced with logout when session started
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_signout: //later to be replaced with some other feature
                //new Session(this).clearSession();
                FirebaseAuth.getInstance().signOut();
                mGoogleSignInClient.signOut().addOnCompleteListener(task -> this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new TrackLoad())
                        .commit());
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.contact_us:
                intent = new Intent(this, NavFrag.class);
                intent.putExtra("page", "contact");
                startActivity(intent);
                break;
            case R.id.about_us:
                intent = new Intent(this, NavFrag.class);
                intent.putExtra("page", "about");
                startActivity(intent);
                break;
            case R.id.help:
                intent = new Intent(this, NavFrag.class);
                intent.putExtra("page", "help");
                startActivity(intent);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mFloatingNavigationView.isOpened()) {
            mFloatingNavigationView.close();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
