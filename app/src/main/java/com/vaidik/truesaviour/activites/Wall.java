package com.vaidik.truesaviour.activites;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vaidik.truesaviour.R;
import com.vaidik.truesaviour.wallpaper.FavouritesFragment;
import com.vaidik.truesaviour.wallpaper.WallpaperHome;

public class Wall extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);

        bottomNavigationView = findViewById(R.id.navigationWall);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        displayFragment(new WallpaperHome());
    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_area, fragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_fav:
                fragment = new FavouritesFragment();
                break;
            case R.id.navigation_set:
                //fragment = new SettingsFragment();
                break;
            default:
                fragment = new WallpaperHome();
                break;
        }
        displayFragment(fragment);
        return true;
    }
}
