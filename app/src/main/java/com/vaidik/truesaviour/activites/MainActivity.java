package com.vaidik.truesaviour.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.vaidik.truesaviour.ActivityTrans.Track;
import com.vaidik.truesaviour.R;
import com.vaidik.truesaviour.UI.Dashboard;
import com.vaidik.truesaviour.UI.Home;
import com.vaidik.truesaviour.UI.NavFrag;
import com.vaidik.truesaviour.Utils.Session;

import java.util.Objects;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView tv_user_id_display;
    Intent intent;

    FloatingNavigationView mFloatingNavigationView;
    private Session session;

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
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Track()).commit();
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
        session = new Session(MainActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        mFloatingNavigationView = findViewById(R.id.nav_view);
        View headerView = mFloatingNavigationView.getHeaderView(0);
        tv_user_id_display = headerView.findViewById(R.id.user_id_display);
        tv_user_id_display.setText(session.getUsername());
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


        Paper.init(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_chat:
                intent = new Intent(this, TSbot.class);
                startActivity(intent);
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
                new Session(this).clearSession();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
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
