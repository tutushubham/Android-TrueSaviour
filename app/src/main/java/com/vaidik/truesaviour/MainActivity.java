package com.vaidik.truesaviour;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static int SPLASH_SCREEN_TIME_OUT = 2000;
    Intent intent;

    FloatingNavigationView mFloatingNavigationView;

    private CoordinatorLayout coordinatorLayout;
    private AnimationDrawable animationDrawable;
    //bottom
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Dashboard()).commit();
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Notif()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        mFloatingNavigationView = findViewById(R.id.nav_view);
        mFloatingNavigationView.setNavigationItemSelectedListener(this);

        mFloatingNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFloatingNavigationView.open();
            }
        });


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();

        coordinatorLayout = findViewById(R.id.container);
        animationDrawable = (AnimationDrawable) coordinatorLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);

        Paper.init(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_chat:
                intent = new Intent(this, TSBot.class);
                startActivity(intent);
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatBox()).commit();
                break;
            case R.id.nav_profile:
                intent = new Intent(this, NavFrag.class);
                intent.putExtra("page", "profile");
                startActivity(intent);
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfilePage()).commit();
                break;
            case R.id.nav_contact:
                intent = new Intent(this, NavFrag.class);
                intent.putExtra("page", "contact");
                startActivity(intent);
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactUs()).commit();
                break;
            case R.id.nav_login:
                intent = new Intent(this, LoginActivity.class);
                //intent.putExtra("page", "contact");
                startActivity(intent);
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactUs()).commit();
                break;
            case R.id.nav_signup:
                intent = new Intent(this, SignUp.class);
                //intent.putExtra("page", "contact");
                startActivity(intent);
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactUs()).commit();
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
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactUs()).commit();
                break;
            case R.id.about_us:
                intent = new Intent(this, NavFrag.class);
                intent.putExtra("page", "about");
                startActivity(intent);
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new About()).commit();
                break;
            case R.id.help:
                intent = new Intent(this, NavFrag.class);
                intent.putExtra("page", "help");
                startActivity(intent);
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Help()).commit();
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
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            // start the animation
            animationDrawable.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            // stop the animation
            animationDrawable.stop();
        }
    }


}
